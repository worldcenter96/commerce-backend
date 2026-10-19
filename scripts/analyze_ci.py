#!/usr/bin/env python3
"""Analyze failed GitHub Actions jobs with the OpenAI Responses API.

Environment: GH_TOKEN, OPENAI_API_KEY, REPOSITORY, RUN_ID.
Requires Python 3.11+; no third-party dependencies.
"""

import json
import os
import re
import sys
import urllib.error
import urllib.parse
import urllib.request
from pathlib import Path

API_ROOT = "https://api.github.com"
OPENAI_URL = "https://api.openai.com/v1/responses"
MODEL = os.getenv("OPENAI_MODEL", "gpt-4.1-mini")
MAX_LOG_CHARS = 18000
FAILED_CONCLUSIONS = {"failure", "timed_out", "action_required"}


def required_env(name):
    value = os.getenv(name, "").strip()
    if not value:
        raise RuntimeError(f"환경변수 {name}이(가) 비어 있습니다.")
    return value


def request_bytes(url, headers=None, data=None, timeout=45):
    request = urllib.request.Request(url, data=data, headers=headers or {})
    with urllib.request.urlopen(request, timeout=timeout) as response:
        return response.read()


def github_json(path, token):
    url = API_ROOT + path
    headers = {
        "Authorization": f"Bearer {token}",
        "Accept": "application/vnd.github+json",
        "X-GitHub-Api-Version": "2022-11-28",
        "User-Agent": "ci-failure-analyzer",
    }
    return json.loads(request_bytes(url, headers=headers))


def failed_jobs(repository, run_id, token):
    page = 1
    found = []
    while True:
        path = f"/repos/{repository}/actions/runs/{run_id}/jobs?per_page=100&page={page}"
        jobs = github_json(path, token).get("jobs", [])
        found.extend(job for job in jobs if job.get("conclusion") in FAILED_CONCLUSIONS)
        if len(jobs) < 100:
            break
        page += 1
    return found


class StopRedirect(urllib.request.HTTPRedirectHandler):
    def redirect_request(self, req, fp, code, msg, headers, newurl):
        return None


def job_log(repository, job_id, token):
    api_url = f"{API_ROOT}/repos/{repository}/actions/jobs/{job_id}/logs"
    req = urllib.request.Request(api_url, headers={
        "Authorization": f"Bearer {token}",
        "Accept": "application/vnd.github+json",
        "X-GitHub-Api-Version": "2022-11-28",
        "User-Agent": "ci-failure-analyzer",
    })
    opener = urllib.request.build_opener(StopRedirect)
    try:
        with opener.open(req, timeout=45) as response:
            raw = response.read()
            return raw.decode("utf-8", errors="replace")
    except urllib.error.HTTPError as error:
        if error.code != 302:
            raise RuntimeError(f"GitHub 로그 API 실패: HTTP {error.code}") from error
        location = error.headers.get("Location")
        if not location:
            raise RuntimeError("GitHub 로그 API가 다운로드 URL을 반환하지 않았습니다.") from error

    parsed = urllib.parse.urlsplit(location)
    hostname = (parsed.hostname or "").lower()
    if parsed.scheme != "https" or not hostname.endswith(".blob.core.windows.net"):
        raise RuntimeError(f"예상하지 못한 로그 다운로드 호스트: {hostname}")
    if not parsed.query:
        raise RuntimeError("다운로드 URL에 인증 쿼리가 없습니다(SAS 쿼리 누락 가능성).")

    # Preserve the *entire* signed URL, including its SAS query string.
    # Never forward GH_TOKEN to Azure, and never print the signed URL.
    try:
        raw = request_bytes(location, headers={"User-Agent": "ci-failure-analyzer"}, timeout=60)

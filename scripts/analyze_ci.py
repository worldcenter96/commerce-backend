import json
import os
import urllib.request
import urllib.error

# os.environ은 운영체제에 설정된 env에 접근하고 관리할 수 있게 해주는 dictionary 형태
# Runner에 등록된 환경변수 사용
GITHUB_TOKEN = os.environ["GITHUB_TOKEN"]
OPENAI_API_KEY = os.environ["OPENAI_API_KEY"]
REPOSITORY = os.environ["REPOSITORY"]
RUN_ID = os.environ["RUN_ID"]
API = "https://api.github.com"


# job_id를 얻기 위한 URL Request
def github_request(path):
    url = API + path

    req = urllib.request.Request(
        url,
        headers={
            "Authorization": f"Bearer {GITHUB_TOKEN}",
            "Accept": "application/vnd.github+json",
            "X-GitHub-Api-Version": "2022-11-28",
            "User-Agent": "gaonnuri"
        }
    )

    try:
        with urllib.request.urlopen(req, timeout=30) as res:
            return res.read()

    except urllib.error.HTTPError as e:
        print(f"Github API 요청 실패: {path}")
        print(f"HTTP 상태 코드: {e.code}")
        raise


# Github Action Job log를 얻기 위한 URL Request
def get_job_log(job_id):
    path = f"/repos/{REPOSITORY}/actions/jobs/{job_id}/logs"
    url = API + path

    req = urllib.request.Request(
        url,
        headers={
            "Authorization": f"Bearer {GITHUB_TOKEN}",
            "Accept": "application/vnd.github+json",
            "X-GitHub-Api-Version": "2022-11-28",
            "User-Agent": "gaonnuri"
        }
    )

    try:
        with urllib.request.urlopen(req, timeout=30) as res:
            print(f"최종 Redirect URL: {res.geturl()}", flush=True)
            return res.read()

    except urllib.error.HTTPError as e:
        print(f"Github API 요청 실패: {path}")
        print(f"HTTP 상태 코드: {e.code}")
        raise


def main():
    print("GitHub Actions 로그 다운로드 테스트 시작")
    # Github Actions Workflow Run에 속한 Job 목록 조회
    path = f"/repos/{REPOSITORY}/actions/runs/{RUN_ID}/jobs"

    res = github_request(path)
    jobs = json.loads(res).get("jobs", [])

    if not jobs:
        print("조회된 job이 없습니다.")
        return

    for job in jobs:
        print(f"Job: {job['name']}, ID: {job['id']}")

    job_id = jobs[0]["id"]

    log_bytes = get_job_log(job_id)
    print(f"로그 다운로드 성공: {len(log_bytes)} bytes")

    with open("job_log.txt", "wb") as f:
        f.write(log_bytes)


if __name__ == "__main__":
    main()

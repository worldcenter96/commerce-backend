import json
import os
import urllib.request
import urllib.error

# os.environ은 운영체제에 설정된 env에 접근하고 관리할 수 있게 해주는 dictionary 형태
# Runner에 등록된 환경변수 사용
GH_TOKEN = os.environ["GH_TOKEN"]
OPENAI_API_KEY = os.environ["OPENAI_API_KEY"]
REPOSITORY = os.environ["REPOSITORY"]
RUN_ID = os.environ["RUN_ID"]
API = "https://api.github.com"


class NoRedirect(urllib.request.HTTPRedirectHandler):
    def redirect_request(
        self, req, fp, code, msg, headers, newurl
    ):
        return None


# job_id를 얻기 위한 URL Request(Job 목록 조회)
def github_request(path):
    url = API + path

    req = urllib.request.Request(
        url,
        headers={
            "Authorization": f"Bearer {GH_TOKEN}",
            "Accept": "application/vnd.github+json",
            "X-GitHub-Api-Version": "2022-11-28"
        }
    )

    try:
        with urllib.request.urlopen(req, timeout=30) as res:
            return res.read()

    except urllib.error.HTTPError as e:
        print(f"Github API 요청 실패: {path}")
        print(f"HTTP 상태 코드: {e.code}")
        print(f"WWW-Authenticate: {e.headers.get('WWW-Authenticate')}")
        raise


# Github Action Job log를 얻기 위한 URL Request
def get_job_log(job_id):
    path = f"/repos/{REPOSITORY}/actions/jobs/{job_id}/logs"
    url = API + path

    req = urllib.request.Request(
        url,
        headers={
            "Authorization": f"Bearer {GH_TOKEN}",
            "Accept": "application/vnd.github+json",
            "X-GitHub-Api-Version": "2022-11-28"
        }
    )

    opener = urllib.request.build_opener(NoRedirect)
    print("[1] GitHub API 로그 요청 시작", flush=True)

    try:
        with opener.open(req, timeout=30) as res:
            print(f"Github API 상태: {res.status}", flush=True)
            return res.read()

    except urllib.error.HTTPError as e:
        print(f"GitHub API 응답 상태: {e.code}", flush=True)

        if e.code != 302:
            print("GitHub API 요청 단계에서 실패")
            print("응답 호스트:", urllib.parse.urlparse(e.url).hostname)
            raise

        download_url = e.headers.get("Location")

        if not download_url:
            raise RuntimeError("Redirect Location 헤더가 없습니다.")

    # 임시 다운로드 URL 확인
    parsed = urllib.parse.urlparse(download_url)

    if parsed.scheme != "https" :
        raise RuntimeError("HTTPS 다운로드 URL이 아닙니다.")

    # 임시 URL로 별도 요청
    download_request = urllib.request.Request(
        download_url,
        method="GET"
    )

    try:
        with urllib.request.urlopen(
            download_request, timeout=60
        ) as res:
            print(f"다운로드 HTTP 상태: {res.status}", flush=True)
            raw = res.read()
            print(f"다운로드 성공: {len(raw)} bytes", flush=True)
            return raw

    except urllib.error.HTTPError as e:
        error_host = urllib.parse.urlparse(e.url).hostname

        print("[4] 다운로드 실패", flush=True)
        print(f"HTTP 상태 코드: {e.code}", flush=True)
        print(f"오류 발생 호스트: {error_host}", flush=True)

        # Azure Storage 오류 코드가 있다면 확인
        print(
            "Azure 오류 코드:",
            e.headers.get("x-ms-error-code"),
            flush=True
        )
        raise


    # try:
    #     with urllib.request.urlopen(req, timeout=30) as res:
    #         print(f"최종 Redirect URL: {res.geturl()}", flush=True)
    #         return res.read()
    #
    # except urllib.error.HTTPError as e:
    #     print(f"Github API 요청 실패: {path}")
    #     print(f"HTTP 상태 코드: {e.code}")
    #     raise


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

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

def github_request(path):
    url = API + path
    headers = {
        "Accept" : "application/vnd.github+json",
        "X-GitHub-Api-Version" : "2022-11-28",
        "User-Agent" : "gaonnuri"
    }

    req = urllib.request.Request(
        url,
        headers=headers,
        method='POST'
    )

    try:
        with urllib.request.urlopen(req, timeout=30) as res:
            return res.read()

    except urllib.error.HTTPError as e:
        print(f"Github API 요청 실패: {path}")
        print(f"HTTP 상태 코드: {e.code}")
        raise

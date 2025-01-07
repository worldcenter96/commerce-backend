# 팀9 커머스 프로젝트

![impostor_commerce_logo.webp](img%2Fimpostor_commerce_logo.webp)

## 프로젝트 목표

의류 커머스 플랫폼을 개발하면서 애자일한 개발 환경 구축과 보안 강화를 1차적인 목표로 설정했습니다. 이를 실현하기 위해 다음과 같은 세부 목표를 수립했습니다:<br>
**1. 외부 통신 차단 + 데이터암호화**
- AWS 인프라를 활용하여 백엔드, MySQL, Redis의 접적인 외부 통신을 차단함으로써 민감 데이터 보호와 데이터 유출을 방지하여 보안성을 강화합니다.


**2. 자동화된 CI/CD 파이프라인 구축**
- GitHub Actions, S3, CodeDeploy를 활용해 자동화된 CI/CD 파이프라인을 구축하여 빠른 배포와 장애 발생 시 롤백이 가능하도록 설정합니다.


**3. 일관된 개발 환경 구성**
- Docker-Compose를 활용해 개발 환경을 표준화하여, 팀원 간 일관된 개발 환경을 유지하고 협업 효율성을 향상시킵니다.


**4. 멀티모듈 구조 도입**
- 프로젝트를 멀티모듈 구조로 설계하여 각 기능별로 독립적인 모듈(admin, b2b, b2c)을 구성했습니다. 
- 멀티모듈 구조는 팀원 간 병렬 작업을 용이하게 하고 특정 모듈에서 발생한 변경이 다른 모듈에 영향을 최소화 시킵니다. 
- 또한, 시스템의 확장성과 유지보수성을 확보합니다.


**5. 문제 원인 분석을 위한 로그데이터**
- Logback을 사용하여 로그를 파일에 기록하고, 로그가 일정 크기를 넘으면 파일 분할(rolling) 및
  백업을 자동으로 처리하도록 설정하습니다. 
- 또한, MDC를 활용하여 traceId를 설정하고, 이를 로그에 포함시켜 요청 흐름을 추적할 수 있도록 했습니다.

## 프로젝트 기간

2024.12.02(월) ~ 2024.01.06(월)

## 팀 구성 👩‍👩‍👧‍👦

| 이름    | 역할  | 담당                                                            |
|-------|-----|---------------------------------------------------------------|
| 허원경   | 팀장  | B2C 모듈 개발, CI/CD 파이프라인 구성, 상품 검색 성능 개선                        |
| 김가빈   | 부팀장 | B2B 모듈 개발, CI/CD 파이프라인 구성, AWS S3 이미지 관리                      |
| 박가온누리 | 팀원  | 인증 및 인가 개발, AWS 인프라 구성, 로그 모니터링 구성, 주문 등록 동시성 제어              |
| 박지예   | 팀원  | Admin 모듈 개발, AWS 인프라 구성, Docker-Compose 구성, Logging, AWS IAM 계정관리 |
| 이시우   | 팀원  | B2C 모듈 개발, Docker-Compose 구성, 주문 조회 성능 개선, 통합테스트              |

## Tools

### 🖥 language & Server 🖥

<img src="https://img.shields.io/badge/intellij idea-207BEA?style=for-the-badge&logo=intellij%20idea&logoColor=white"> <br>
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> <br>
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/redis-283272?style=for-the-badge&logo=redis&logoColor=white"> <br>
<img src="https://img.shields.io/badge/amazon-FF9900?style=for-the-badge&logo=amazon&logoColor=black"> <img src="https://img.shields.io/badge/LocalStack-ED1944?style=for-the-badge&logo=LocalStack&logoColor=black"> <br>
<img src="https://img.shields.io/badge/dockercompose-2496ED?style=for-the-badge&logo=docker&logoColor=black">
<hr> 

### 👏 Cowork Tools 👏

<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"> <br> 
<img src="https://img.shields.io/badge/notion-000000?style=or-the-badge&logo=notion&logoColor=white"/> <img src="https://img.shields.io/badge/Slack-FE5196?style=or-the-badge&logo=slack&logoColor=white"/>
<br> 
<hr/>



## 🐳 Docker-Compose 로 실행환경 세팅하기
1. 해당 프로젝트를 clone 한 뒤, docker-compose를 실행합니다.
```bash
docker-compose up
```
2. docker-compose를 실행하면 Redis, MySQL, LocalStack 컨테이너가 실행됩니다.
3. 모든 컨테이너가 정상 실행되면 Admin, B2B, B2C 모듈별로 애플리케이션을 실행합니다.
```bash
# admin 모듈 실행
./gradlew :admin:bootRun

# b2b 모듈 실행
./gradlew :b2b:bootRun

# b2c 모듈 실행
./gradlew :b2c:bootRun
```

4. 앱 실행이 완료되면 `scripts > impostor_commerce.postman_collection.json` 파일을 Postman에 Import하여 앱에 요청을 보낼 수 있습니다.

### 애자일 개발 프로세스 채택
![agile_process.png](img%2Fagile_process.png)

## 인프라 아키텍처

![impostor_team9_Infra_arch_v1.0.drawio.png](img%2Fimpostor_team9_Infra_arch_v1.0.drawio.png)

<br>

- **상세 아키텍처**
![impostor_team9_Infra_arch_v2.0.drawio.png](img%2Fimpostor_team9_Infra_arch_v2.0.drawio.png)

<br>

- **로깅 모니터링 아키텍처**

<br>

![impostor_team9_logging_drawio.png](img%2Fimpostor_team9_logging_drawio.png)
## 와이어프레임

### 회원가입 및 로그인

![wireframe_member.jpeg](img%2Fwireframe_member.jpeg)

### 소비자 사이트

![wireframe_b2c.png](img%2Fwireframe_b2c.png)

### 판매자 사이트

![wireframe_b2b.png](img%2Fwireframe_b2b.png)

### 관리자 사이트

![wireframe_admin.jpeg](img%2Fwireframe_admin.jpeg)

## ERD 명세서

![erd_frame.png](img%2Ferd_frame.png)

## 프로젝트 구조
```text
admin 모듈
  ├─healthz
  │   └─controller
  ├─member 
  │   ├─controller
  │   ├─dto
  │   └─service
  └─product
      ├─controller
      ├─dto
      └─service
  
b2b 모듈
  ├─fileUpload
  │   ├─controller
  │   ├─dto
  │   ├─scheduler
  │   └─service
  ├─healthz
  │   └─controller
  ├─member 
  │   ├─controller
  │   ├─dto
  │   └─service
  ├─order
  │   ├─controller
  │   ├─dto
  │   └─service
  └─product
      ├─controller
      ├─dto
      └─service

b2c 모듈
  ├─healthz
  │   └─controller
  ├─member 
  │   ├─controller
  │   ├─dto
  │   └─service
  ├─order
  │   ├─controller
  │   ├─dto
  │   └─service
  └─product
      ├─controller
      ├─dto
      └─service
      
 common 모듈
  ├─annotation
  ├─aspect
  ├─config
  ├─dto
  ├─enums
  ├─interceptor
  ├─logging
  ├─resolver
  ├─service
  └─utils
  
domain 모듈
  ├─adminmember
  │   ├─entity
  │   └─repository
  ├─b2cmember
  │   ├─entity
  │   ├─enums
  │   └─repository
  ├─b2bmember
  │   ├─entity
  │   ├─enums
  │   └─repository
  ├─image
  │   ├─entity
  │   └─repository
  ├─order
  │   ├─entity
  │   ├─enums
  │   └─repository
  └─product
      ├─entity
      ├─enums
      └─repository
```



## API 명세서

### 관리자 API 명세

<details>
  <summary>관리자 API 명세서</summary>
<table>
    <tr>
        <th>API&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
        <th>Method</th>
        <th>URL</th>
        <th>Request</th>
        <th>Request Type</th>
        <th>Status</th>
    </tr>
    <tr>
        <td>회원가입</td>
        <td>POST</td>
        <td><code>http://localhost:8080/api/admin-members/signup</code></td>
        <td><pre lang="json">{
  "email": "admin001@email.com",
  "password": "12341234",
  "name": "관리자001"
}</pre></td>
        <td><code>application/json</code></td>
        <td>201</td>
    </tr>
     <tr>
        <td>로그인</td>
        <td>POST</td>
        <td><code>http://localhost:8080/api/admin-members/login</code></td>
        <td><pre lang="json">{
  "email": "admin001@email.com",
  "password": "12341234"
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
    </tr>
    <tr>
        <td>소비자 회원 조회</td>
        <td>GET</td>
        <td><code>http://localhost:8080/api/admin/b2c-members?page=1&size=10&sortBy=name&orderBy=ASC&status=ACTIVE</code></td>
        <td>N/A</td>
        <td><code>RequestParam</code></td>
        <td>200</td>
    </tr>
    <tr>
        <td>판매자 회원 조회</td>
        <td>GET</td>
        <td><code>http://localhost:8080/api/admin/b2b-members?page=1&size=10&sortBy=name&orderBy=ASC</code></td>
        <td>N/A</td>
        <td><code>RequestParam</code></td>
        <td>200</td>
    </tr>
    <tr>
        <td>판매자 등록 요청 승인 및 거절</td>
        <td>PATCH</td>
        <td><code>http://localhost:8080/api/admin/b2b/{memberId}/approve</code></td>
        <td><pre lang="json">{
  "status": "ACTIVE"
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
    </tr>
    <tr>
        <td>소비자 회원 상태 변경</td>
        <td>PATCH</td>
        <td><code>http://localhost:8080/api/admin/b2c/{memberId}/update-status</code></td>
        <td><pre lang="json">{
  "status": "INACTIVE"
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
    </tr>
    <tr>
        <td>판매자 상품 등록 승인 및 거절</td>
        <td>PATCH</td>
        <td><code>http://localhost:8080/api/admin/products/approval/{productId}</code></td>
        <td><pre lang="json">{
  "status": "ON_SALE"
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
    </tr>
    <tr>
        <td>판매자 상품 전체 조회</td>
        <td>GET</td>
        <td><code>http://localhost:8080/api/admin/products?status=PENDING&page=1&size=10&orderBy=ASC&sortBy=price</code></td>
        <td>N/A</td>
        <td><code>RequestParam</code></td>
        <td>200</td>
    </tr>
</table>
</details>

### 판매자 API 명세

<details>
  <summary>판매자 API 명세서</summary>
<table>
    <tr>
        <th>API&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
        <th>Method</th>
        <th>EndPoint</th>
        <th>Request</th>
        <th>Request Type</th>
        <th>Status</th>
    </tr>
    <tr>
        <td>회원가입</td>
        <td>POST</td>
        <td><code>http://localhost:8081/api/b2b-members/signup</code></td>
        <td><pre lang="json">{
  "email": "seller001@email.com",
  "password": "12341234",
  "name": "판매자001"
}</pre></td>
        <td><code>application/json</code></td>
        <td>201</td>
    </tr>
     <tr>
        <td>로그인</td>
        <td>POST</td>
        <td><code>http://localhost:8081/api/b2b-members/login</code></td>
        <td><pre lang="json">{
  "email": "seller001@email.com",
  "password": "12341234"
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
    </tr>
    <tr>
        <td>상품 등록</td>
        <td>POST</td>
        <td><code>http://localhost:8081/api/products</code></td>
        <td><pre lang="json">{
 "name": "상품이름",
 "description": "상품내용",
 "stockQuantity": 10,
 "price": 10000,
 "category": "none",
 "subCategory": "none",
 "images": [
     {"url" : "http://example.com/api/upload"},
     {"url" : "http://example.com/api/upload"},
     {"url" : "http://example.com/api/upload"}	  
 ... ] 
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
    </tr>
    <tr>
        <td>전체 상품 조회</td>
        <td>GET</td>
        <td><code>http://localhost:8081/api/products?page=1&size=10&orderBy=ASC&sortBy=price</code></td>
        <td>N/A</td>
        <td><code>RequestParam</code></td>
        <td>200</td>
    </tr>
    <tr>
        <td>특정 상품 조회</td>
        <td>GET</td>
        <td><code>http://localhost:8081/api/products/{productId}</code></td>
        <td>N/A</td>
        <td><code>PathVariable</code></td>
        <td>200</td>
    </tr>
    <tr>
        <td>상품 삭제</td>
        <td>DELETE</td>
        <td><code>http://localhost:8081/api/products/{productId}</code></td>
        <td>N/A</td>
        <td><code>PathVariable</code></td>
        <td>204</td>
    </tr>
    <tr>
        <td>S3 이미지 업로드</td>
        <td>POST</td>
        <td><code>http://localhost:8081/api/products/images</code></td>
        <td>스크린샷 2024-12-13 150637.png</td>
        <td>image/*</td>
        <td>200</td>
    </tr>
    <tr>
        <td>전체 주문 조회</td>
        <td>GET</td>
        <td><code>http://localhost:8081/api/orders?page=1&size=10</code></td>
        <td>N/A</td>
        <td><code>RequestParam</code></td>
        <td>200</td>
    </tr>
    <tr>
        <td>배송 상태 변경</td>
        <td>PATCH</td>
        <td><code>http://localhost:8081/api/orders/{orderId}/delivery-status</code></td>
        <td><pre lang="json">{
    "trackingNumber": "123456789012"
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
    </tr>
</table>
</details>

### 소비자 API 명세
<details>
  <summary>소비자 API 명세서</summary>
<table>
    <tr>
        <th>API&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
        <th>Method</th>
        <th>EndPoint</th>
        <th>Request</th>
        <th>Request Type</th>
        <th>Status</th>
    </tr>
    <tr>
        <td>회원가입</td>
        <td>POST</td>
        <td><code>http://localhost:8082/api/b2c-members/signup</code></td>
        <td><pre lang="json">{
  "email": "user001@email.com",
  "password": "12341234",
  "name": "유저001"
}</pre></td>
        <td><code>application/json</code></td>
        <td>201</td>
    </tr>
     <tr>
        <td>로그인</td>
        <td>POST</td>
        <td><code>http://localhost:8082/api/b2c-members/login</code></td>
        <td><pre lang="json">{
  "email": "user001@email.com",
  "password": "12341234"
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
    </tr>
    <tr>
        <td>주문 생성</td>
        <td>POST</td>
        <td><code>http://localhost:8082/api/orders</code></td>
        <td><pre lang="json">{
  "productId": 1,
  "quantity": 3
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
    </tr>
    <tr>
        <td>전체 주문 조회</td>
        <td>GET</td>
        <td><code>http://localhost:8082/api/orders?page=1&size=10&sortBy=quantity&orderBy=ASC</code></td>
        <td>N/A</td>
        <td><code>RequestParam</code></td>
        <td>200</td>
    </tr>
    <tr>
        <td>특정 주문 조회</td>
        <td>GET</td>
        <td><code>http://localhost:8082/api/orders/{orderId}</code></td>
        <td>N/A</td>
        <td><code>PathVariable</code></td>
        <td>200</td>
    </tr>
    <tr>
        <td>상품 검색</td>
        <td>GET</td>
        <td><code>http://localhost:8082/api/products?keyword=상품명&orderBy=ASC</code></td>
        <td>N/A</td>
        <td><code>RequestParam</code></td>
        <td>200</td>
    </tr>
    <tr>
        <td>특정 상품 조회</td>
        <td>GET</td>
        <td><code>http://localhost:8082/api/products/{productId}</code></td>
        <td>N/A</td>
        <td><code>PathVariable</code></td>
        <td>200</td>
    </tr>
    <tr>
        <td>연관 상품 조회</td>
        <td>GET</td>
        <td><code>http://localhost:8082/api/related-products</code></td>
        <td>N/A</td>
        <td>N/A</td>
        <td>200</td>
    </tr>
</table>
</details>



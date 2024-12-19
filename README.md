# 팀9 커머스 프로젝트

![impostor_commerce_logo.webp](img%2Fimpostor_commerce_logo.webp)

## 프로젝트 목표

평소 자주 사용하는 쿠팡, 마켓컬리, 에이블리와 같은 커머스 플랫폼을 구현하면서, 해당 프로젝트에서 발생할 수 있는 트래픽 성능 개선을 주요 목표로 설정하였습니다.<br>
트래픽 성능 개선을 위해 세 가지 세부 목표를 수립하였습니다.

1. 트래픽이 몰릴 때 발생할 수 있는 동시성 문제를 해결합니다.
2. 데이터베이스 쿼리를 최적화하여 효율성을 높입니다.
3. 셋째, 캐싱을 활용해 응답 속도를 개선합니다.

## 프로젝트 기간

2024.12.02(월) ~ 2024.01.16(목)

## 팀 구성 👩‍👩‍👧‍👦

| 이름    | 역할  | 담당 기능                        |
|-------|-----|------------------------------|
| 허원경   | 팀장  | B2C 모듈 개발, CI/CD 파이프라인 구성    |
| 김가빈   | 부팀장 | B2B 모듈 개발, CI/CD 파이프라인 구성    |
| 박가온누리 | 팀원  | 인증 및 인가 개발, AWS 인프라 구성       |
| 박지예   | 팀원  | Admin 모듈 개발, AWS 인프라 구성      |
| 이시우   | 팀원  | B2C 모듈 개발, Docker-Compose 구성 |

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

## 인프라 아키텍처

![impostor_team9_Infra_arch_v1.0.drawio.png](img%2Fimpostor_team9_Infra_arch_v1.0.drawio.png)

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



# 팀9 커머스 프로젝트
![coupang.jpg](img%2Fcoupang.jpg)

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
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <hr> 

### 👏 Cowork Tools 👏

<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"> <br> 
<img src="https://img.shields.io/badge/notion-000000?style=or-the-badge&logo=notion&logoColor=white"/> <img src="https://img.shields.io/badge/Slack-FE5196?style=or-the-badge&logo=slack&logoColor=white"/>
<br>
<hr/>

## 와이어프레임
### 회원가입 및 로그인
![wireframe_member.jpeg](img%2Fwireframe_member.jpeg)

### 소비자 사이트
![wireframe_b2c.png](img%2Fwireframe_b2c.png)

### 판매자 사이트
![wireframe_b2b.png](img%2Fwireframe_b2b.png)

### 관리자 사이트
![wireframe_admin.jpeg](img%2Fwireframe_admin.jpeg)

## API 명세서
### 관리자 API 명세
<table>
    <tr>
        <th>API&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
        <th>Method</th>
        <th>EndPoint</th>
        <th>Request</th>
        <th>Request Type</th>
        <th>Response</th>
        <th>Response Type</th>
        <th>Status</th>
    </tr>
    <tr>
        <td>회원가입</td>
        <td>POST</td>
        <td><code>/api/admin-members/signup</code></td>
        <td><pre lang="json">{
    "email": "admin@example.com",
    "password": "123456789",
    "name": "홍길동"
}</pre></td>
        <td><code>application/json</code></td>
        <td><pre lang="json">{
"id": 1,
"email": "admin@example.com",
"name": "홍길동",
"createdAt": "1111-11-11 14:00:00",
"modifiedAt": "1111-11-11 14:00:00"
}</pre></td>
        <td><code>application/json</code></td>
        <td>201</td>
    </tr>
     <tr>
        <td>로그인</td>
        <td>POST</td>
        <td><code>/api/admin-members/login</code></td>
        <td><pre lang="json">{
"email": "admin@gmail.com",
"password": "Password1234!"
}</pre></td>
        <td><code>application/json</code></td>
        <td>N/A</td>
        <td>N/A</td>
        <td>200</td>
    </tr>
    <tr>
        <td>소비자 회원 조회</td>
        <td>GET</td>
        <td><code>/api/admin/b2c-members</code></td>
        <td>N/A</td>
        <td>N/A</td>
        <td><pre lang="json">{
  "contents": [
    {
      "id": 1,
      "email": "user1@example.com",
      "name": "존도",
      "status": "ACTIVE",
    },
    {
      "id": 2,
      "email": "user2@example.com",
      "name": "길동홍",
      "status": "INACTIVE"
    }
  ]
}
"page": 1,
"size": 10,
"sortBy": "name",
"orderBy": "desc",
"totalpage": 5
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
    </tr>
    <tr>
        <td>판매자 회원 조회</td>
        <td>GET</td>
        <td><code>/api/admin/b2b-members</code></td>
        <td>N/A</td>
        <td>N/A</td>
        <td><pre lang="json">{
  "members": [
    {
      "id": 1,
      "email": "b2buser1@example.com",
      "name": "제인도",
      "status": "ACTIVE"
    },
    {
      "id": 2,
      "email": "b2buser2@example.com",
      "name": "베이비도",
      "status": "INACTIVE"
    }
  ]
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
    </tr>
    <tr>
        <td>판매자 등록 요청 승인 및 거절</td>
        <td>PATCH</td>
        <td><code>/api/admin/b2c/{id}/approve</code></td>
        <td><pre lang="json">{
  "status": "ACTIVE"
}</pre></td>
        <td><code>application/json</code></td>
        <td><pre lang="json">{
"id": 1234,
"email": "example@domain.com",
"name": "존 도",
"activeStatus": "ACTIVE",
"createdAt": "2024-12-06T00:00:00",
"modifiedAt": "2024-12-06T00:00:00"
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
    </tr>
    <tr>
        <td>소비자 회원 상태 변경</td>
        <td>PATCH</td>
        <td><code>/api/admin/b2c/{id}/update-status</code></td>
        <td><pre lang="json">{
  "status": "INACTIVE"
}</pre></td>
        <td><code>application/json</code></td>
        <td><pre lang="json">{
"id": 1234,
"email": "example@domain.com",
"name": "존 도",
"status": "INACTIVE",
"message": "B2C 회원 상태가 변경되었습니다."
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
    </tr>
    <tr>
        <td>판매자 상품 등록 승인 및 거절</td>
        <td>PATCH</td>
        <td><code>/api/admin/products/approval/{productId}</code></td>
        <td><pre lang="json">{
  "status": "ON_SALE"
}</pre></td>
        <td><code>application/json</code></td>
        <td>N/A</td>
        <td>N/A</td>
        <td>200</td>
    </tr>
    <tr>
        <td>판매자 상품 전체 조회</td>
        <td>GET</td>
        <td><code>/api/admin/products</code></td>
        <td>N/A</td>
        <td>N/A</td>
        <td><pre lang="json">{
  "contents": [
      {
        "id": 1,
        "name": "상품 이름",
        "description": "상품 내용",
        "stockQuantity": "상품 수량",
        "price": 10000,
        "status": "ON_SALE",
        "category": "",
        "subCategory": "",
        "createdAt": "2024-12-04 00:00:00",
        "modifiedAt": "2024-12-04 00:00:00",
        "b2b-members": {
          "id": 1,
          "email": "b2buser1@example.com",
          "name": "제인도",
          "status": "ACTIVE",
          "role": "SELLER"
        }
      }, {...}, {...}
    ]
  "page": 1,
  "size": 20,
  "sortBy": "name",
  "orderBy": "asc"
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
    </tr>
</table>

### 판매자 API 명세
<table>
    <tr>
        <th>API&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
        <th>Method</th>
        <th>EndPoint</th>
        <th>Request</th>
        <th>Request Type</th>
        <th>Response</th>
        <th>Response Type</th>
        <th>Status</th>
    </tr>
    <tr>
        <td>회원가입</td>
        <td>POST</td>
        <td><code>/api/b2b-members/signup</code></td>
        <td><pre lang="json">{
    "email": "b2b@example.com",
    "password": "123456789",
    "name": "홍길동"
}</pre></td>
        <td><code>application/json</code></td>
        <td><pre lang="json">{
"id": 1,
"email": "b2b@example.com",
"name": "홍길동",
"status": "PENDING",
"createdAt": "1111-11-11 14:00:00",
"modifiedAt": "1111-11-11 14:00:00"
}</pre></td>
        <td><code>application/json</code></td>
        <td>201</td>
    </tr>
     <tr>
        <td>로그인</td>
        <td>POST</td>
        <td><code>/api/b2b-members/login</code></td>
        <td><pre lang="json">{
    "email": "b2b@example.com",
    "password": "123456789"
}</pre></td>
        <td><code>application/json</code></td>
        <td>N/A</td>
        <td>N/A</td>
        <td>200</td>
    </tr>
    <tr>
        <td>상품 등록</td>
        <td>POST</td>
        <td><code>/api/products</code></td>
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
        <td><pre lang="json">{
 "id": 1,
 "name": "상품이름",
 "description": "상품내용",
 "stockQuantity": 10,
 "price": 10000,
 "status": "PENDING",
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
        <td><code>/api/products</code></td>
        <td>N/A</td>
        <td>N/A</td>
        <td><pre lang="json">{
 "contents" : [
{"id": 1,
"name": "상품이름",
"description": "상품내용",
"stockQuantity": 10,
"price": 10000,
"status": "PENDING",
"category": "none",
"subCategory": "none",
"totalOrders": 234,
"images": [
{"url" : "http://example.com/api/upload"},
{"url" : "http://example.com/api/upload"},
{"url" : "http://example.com/api/upload"}	  
]},
{"id": 2,
"name": "상품이름2",
"description": "상품내용2",
"stockQuantity": 20,
"price": 10000,
"status": "PENDING",
"category": "none",
"subCategory": "none",
"totalOrders": 838,
"images": [
{"url" : "http://example.com/api/upload"},
{"url" : "http://example.com/api/upload"},
{"url" : "http://example.com/api/upload"}
]},
...],
"page": 1,
"size": 10,
"totalpage": 5
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
    </tr>
    <tr>
        <td>특정 상품 조회</td>
        <td>GET</td>
        <td><code>/api/products/{Id}</code></td>
        <td>N/A</td>
        <td>N/A</td>
        <td><pre lang="json">{
 "id" : 1,
 "name" : "상품이름",
 "description" : "상품내용",
 "stockQuantity" : "10",
 "price" : "10000",
 "status" : "PENDING",
 "category" : "none",
 "subCategory" : "none",
 "totalOrders": 234,
 "contents": [
     {"url" : "http://example.com/api/upload"},
     {"url" : "http://example.com/api/upload"},
     {"url" : "http://example.com/api/upload"}	  
 ... ] 
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
    </tr>
    <tr>
        <td>판매자 상품 재고 수정</td>
        <td>PATCH</td>
        <td><code>/api/products/{Id}/stock-quantity</code></td>
        <td><pre lang="json">{
	"stockQuantity" : "5"
}</pre></td>
        <td><code>application/json</code></td>
        <td><pre lang="json">{
 "id": 1,
 "name": "상품이름",
 "description": "상품내용",
 "stockQuantity": 5,
 "price": 10000,
 "status": "PENDING",
 "category": "none",
 "subCategory": "none",
 "totalOrders": 234,
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
    </tr>
    <tr>
        <td>상품 삭제</td>
        <td>DELETE</td>
        <td><code>/api/products/{Id}</code></td>
        <td>N/A</td>
        <td>N/A</td>
        <td>N/A</td>
        <td>N/A</td>
        <td>204</td>
    </tr>
    <tr>
        <td>S3 이미지 업로드</td>
        <td>POST</td>
        <td><code>/api/products/images</code></td>
        <td>N/A</td>
        <td>N/A</td>
        <td><pre lang="json">{
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
        <td>전체 주문 조회</td>
        <td>GET</td>
        <td><code>/api/orders</code></td>
        <td>N/A</td>
        <td>N/A</td>
        <td><pre lang="json">[
  {
    "name": "핸드크림",
    "trackingNumber": "12314324234234!",
    "orderStatus": "CONFIRMED",
    "deliveryStatus": "NOT_SHIPPED",
    "totalPrice": 54000,
    "quantity": 3,
    "createdAt": "1111-11-11 14:00:00",
    "modifiedAt": "1111-11-11 14:00:00"
  },
  {
    "name": "핸드크림",
    "trackingNumber": "12314324234234!",
    "orderStatus": "CONFIRMED",
    "deliveryStatus": "NOT_SHIPPED",
    "totalPrice": 54000,
    "quantity": 3,
    "createdAt": "1111-11-11 14:00:00",
    "modifiedAt": "1111-11-11 14:00:00"
  },
  "totalPages": 2,
  "totalElements": 2
]</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
    </tr>
    <tr>
        <td>배송 상태 변경</td>
        <td>PATCH</td>
        <td><code>/api/orders/{orderId}/delivery-status</code></td>
        <td><pre lang="json">{
    "trackingNumber": "123456789012"
}</pre></td>
        <td><code>application/json</code></td>
        <td><pre lang="json">{
    "id": 2,
    "name": "손선풍기",
    "trackingNumber": "123456789012",
    "orderStatus": "CONFIRMED",
    "deliveryStatus": "IN_TRANSIT",
    "totalPrice": 10000,
    "quantity": 2,
    "memberName": "John Doe",
    "productName": "손선풍기",
    "createdAt": "2024-12-05T23:23:27",
    "modifiedAt": "2024-12-06T13:33:04.985627"
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
    </tr>
</table>

### 소비자 API 명세
<table>
    <tr>
        <th>API&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
        <th>Method</th>
        <th>EndPoint</th>
        <th>Request</th>
        <th>Request Type</th>
        <th>Response</th>
        <th>Response Type</th>
        <th>Status</th>
    </tr>
    <tr>
        <td>회원가입</td>
        <td>POST</td>
        <td><code>/api/b2c-members/signup</code></td>
        <td><pre lang="json">{
    "email": "b2c@example.com",
    "password": "123456789",
    "name": "홍길동"
}</pre></td>
        <td><code>application/json</code></td>
        <td><pre lang="json">{
"id": 1,
"email": "b2c@example.com",
"name": "홍길동",
"status": "ACTIVE",
"createdAt": "1111-11-11 14:00:00",
"modifiedAt": "1111-11-11 14:00:00"
}</pre></td>
        <td><code>application/json</code></td>
        <td>201</td>
    </tr>
     <tr>
        <td>로그인</td>
        <td>POST</td>
        <td><code>/api/b2c-members/login</code></td>
        <td><pre lang="json">{
    "email": "b2c@example.com",
    "password": "123456789"
}</pre></td>
        <td><code>application/json</code></td>
        <td>N/A</td>
        <td>N/A</td>
        <td>200</td>
    </tr>
    <tr>
        <td>주문 생성</td>
        <td>POST</td>
        <td><code>/api/orders</code></td>
        <td><pre lang="json">{
  "productId": 1,
  "quantity": 3
}</pre></td>
        <td><code>application/json</code></td>
        <td><pre lang="json">{
  "id": 1,
  "name": "상품 이름",
  "quantity": 3,
  "totalPrice": 54000,
  "orderStatus": "CONFIRMED",
  "deliveryStatus": "NOT_SHIPPED",
  "trackingNumber": "",
  "createdAt" : "2024-12-04T00:00:00",
  "modifiedAt" : "2024-12-04T00:00:00"
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
    </tr>
    <tr>
        <td>전체 주문 조회</td>
        <td>GET</td>
        <td><code>/api/orders</code></td>
        <td>N/A</td>
        <td>N/A</td>
        <td><pre lang="json">{
  "contents": [
 {
  "id" : 1,
  "name" : "상품 이름",
  "quantity" : 3,
  "totalPrice" : 54000,
  "orderStatus" : "CONFIRMED",
  "deliveryStatus": "IN_TRANSIT",
  "createdAt" : "2024-12-04T00:00:00",
  "modifiedAt" : "2024-12-05T00:00:00"
 },
    {
      ...
    }
  ],
    "page": 1,
    "size": 10,
    "sortBy": "modifiedAt",
    "orderBy": "desc",
    "totalPages": 5
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
    </tr>
    <tr>
        <td>특정 주문 조회</td>
        <td>GET</td>
        <td><code>/api/orders/{id}</code></td>
        <td>N/A</td>
        <td>N/A</td>
        <td><pre lang="json">{
  "id" : 1,
  "name" : "상품 이름",
  "quantity" : 3,
  "totalPrice" : 54000,
  "orderStatus" : "CONFIRMED",
  "deliveryStatus": "IN_TRANSIT",
  "createdAt" : "2024-12-04T00:00:00",
  "modifiedAt" : "2024-12-05T00:00:00"
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
    </tr>
    <tr>
        <td>주문 환불</td>
        <td>PATCH</td>
        <td><code>/api/orders/{id}/order-status</code></td>
        <td><pre lang="json">{
  "orderStatus" : "REFUND_REQUEST"
}</pre></td>
        <td><code>application/json</code></td>
        <td><pre lang="json">{
  "id" : 1,
  "name" : "상품 이름",
  "quantity" : 3,
  "totalPrice" : 54000,
  "orderStatus" : "REFUND_REQUEST",
  "deliveryStatus": "NOT_SHIPPED",
  "trackingNumber": " ",
  "createdAt" : "2024-12-04T00:00:00",
  "modifiedAt" : "2024-12-05T00:00:00"
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
    </tr>
    <tr>
        <td>상품 검색</td>
        <td>GET</td>
        <td><code>/api/products</code></td>
        <td>N/A</td>
        <td>N/A</td>
        <td><pre lang="json">{
  "orderStatus" : "REFUND_REQUEST"
}</pre></td>
        <td><code>application/json</code></td>
        <td><pre lang="json">{
  "content": [
    {
      "id": 1,
      "name": "상품 이름",
      "description": "상품 내용",
      "stockQuantity": "상품 수량",
      "price": 10000,
      "status": "ON_SALE",
      "category": "TOP",
      "subCategory": "T_SHIRT",
      "createdAt": "2024-12-04 00:00:00",
      "modifiedAt": "2024-12-04 00:00:00"
    },
    {
      ...
    }
  ]
  "page": 1,
  "size": 10,
  "totalPage": 5
}</pre></td>
        <td><code>application/json</code></td>
        <td>204</td>
    </tr>
    <tr>
        <td>특정 상품 조회</td>
        <td>GET</td>
        <td><code>/api/products/{id}</code></td>
        <td>N/A</td>
        <td>N/A</td>
        <td><pre lang="json">{
  "id": 1,
  "name": "상품 이름",
  "description": "상품 내용",
  "stockQuantity": "상품 수량",
  "price": 10000,
  "status": "ON_SALE",
  "category": "TOP",
  "subCategory": "T_SHIRT",
  "createdAt": "2024-12-04 00:00:00",
  "modifiedAt": "2024-12-04 00:00:00"
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
    </tr>
    <tr>
        <td>연관 상품 조회</td>
        <td>GET</td>
        <td><code>/api/related-products</code></td>
        <td>N/A</td>
        <td>N/A</td>
        <td><pre lang="json">{
  "contents": [
    {
      "productId": 1,
      "name": "상품 이름",
      "description": "상품 내용",
      "stockQuantity": "상품 수량",
      "price": 10000,
      "status": "ON_SALE",
      "category": "TOP",
      "subCategory": "T_SHIRT",
      "createdAt": "2024-12-04 00:00:00",
      "modifiedAt": "2024-12-04 00:00:00"
    },
    {
      ...
    }
  ]
  "page": 1,
  "size": 10,
  "totalPage": 1
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
    </tr>
</table>



## ERD 명세서
![erd_frame.png](img%2Ferd_frame.png)




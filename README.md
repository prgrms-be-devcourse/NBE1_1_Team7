# NBE1_1_Team7
Programmers DevCourse BE 1기 7팀 1차 팀 프로젝트

## 0️⃣ 프로젝트 소개

### 프로젝트 명칭
로컬 카페 **Grids & Circles**

### 1차 프로젝트 참고 자료
- [1차 프로젝트](https://www.notion.so/1-3953906e07df4359ac6110d5f5199bef)

### 개발 기간
**2024/09/06 ~ 2024/09/12**

## 1️⃣ 기획서
- [1차 팀 프로젝트 기획서](https://www.notion.so/1-f52a96adbed24df6b55e350170e24bad)

## 2️⃣ 개발 툴
| Software | 세부 Spec 사양 (Version) |
| --- | --- |
| Java | Java SE 17.0.11 |
| Spring Boot | 3.3.3 |
| Spring Boot Libraries | Data JPA, Web, Validation, Security: 2.1.0 |
| Lombok | 1.18.20 |
| QueryDSL | JPA: 5.0.0 |
| Swagger/OpenAPI | SpringDoc OpenAPI MVC UI: 2.1.0 |
| MySQL | MySQL Community 8.0.39 |
| MySQL Connector | 8.0.29 |
| H2 Database | 2.1.214 (Test 용도) |
| JUnit | JUnit Platform Launcher: 1.9.2 |

## 3️⃣ 프로젝트 협업 규칙 
- [브랜치, 풀리퀘스트 전략](docs/Branch%20strategy%20and%20pull-quest.md)
- [코드 컨벤션](https://www.notion.so/Code-Convention-39b695d898124b9c9d92214b83d84fc8)
- [커밋 컨벤션](docs/Define%20a%20commit%20message%20convention.md)

## 4️⃣ 프로젝트 구현

1. **주요 기능 요약**:
   - **회원 관리**: 회원가입, 로그인/로그아웃, 개인 정보 조회 및 수정
   - **관리자**: 상품, 배송, FAQ 관리
   - **사용자**: 상품 주문, 조회, 검색, 장바구니, 리뷰
   
2. **데이터베이스 연동**:
   - **MySQL**: 실제 운영 데이터베이스로 사용. 회원, 주문, 상품, 장바구니, 리뷰, FAQ 데이터을 저장.
   - **H2 Database**: 테스트 용도로 사용. 개발 및 테스트 환경에서 사용.

3. **Spring Security**:
   - **간단한 인증 및 인가 구현**: API 보호를 위한 기본적인 인증 로직 사용.

4. **테스트**:
   - **JUnit을 이용한 유닛 테스트**: 각 서비스 로직 및 컨트롤러에 대한 테스트 구현.

5. **Swagger API 문서화**:
   - SpringDoc을 활용하여 API 문서 자동 생성.
   - `/swagger-ui.html`에서 API 문서 확인 가능.

6. **Sequence Diagram**
- [Sequence Diagram](docs/Sequence%20Diagram.md)

7. **ERD**
<img width="795" alt="image" src="https://github.com/user-attachments/assets/bfd2c2b2-47c3-452a-8d41-ee27bbb57286">

8. **프로젝트 패키지 구조**
```
com
└── grepp
    └── coffee
        └── backend
            ├── common
            │   └── exception
            │       ├── member
            │       │   ├── CartException.java
            │       │   └── MemberException.java
            │       ├── order
            │       │   └── OrderException.java
            │       ├── product
            │       │   └── ProductException.java
            │       ├── question
            │       │   └── QuestionException.java
            │       ├── review
            │       │   └── ReviewException.java
            │       ├── CoffeeException.java
            │       ├── ErrorResponse.java
            │       ├── ExceptionMessage.java
            │       └── GlobalExceptionHandler.java
            ├── config
            │   ├── QueryDslConfig.java
            │   └── SecurityConfig.java
            ├── controller
            │   ├── member
            │   │   ├── request
            │   │   │   ├── CartRegisterRequest.java
            │   │   │   ├── CartUpdateRequest.java
            │   │   │   ├── MemberLoginRequest.java
            │   │   │   ├── MemberRegisterRequest.java
            │   │   │   └── MemberUpdateRequest.java
            │   │   ├── CartController.java
            │   │   └── MemberController.java
            │   ├── order
            │   │   ├── request
            │   │   │   ├── OrderRegisterRequest.java
            │   │   │   └── OrderUpdateRequest.java
            │   │   └── OrderController.java
            │   ├── orderitem
            │   │   ├── request
            │   │   │   └── OrderItemRequest.java
            │   ├── product
            │   │   ├── request
            │   │   │   ├── ProductDetailResponse.java
            │   │   │   ├── ProductRegisterRequest.java
            │   │   │   └── ProductUpdateRequest.java
            │   │   ├── ProductController.java
            │   │   └── ProductSearchController.java
            │   ├── question
            │   │   ├── request
            │   │   │   ├── QuestionDeleteRequest.java
            │   │   │   ├── QuestionRegisterRequest.java
            │   │   │   └── QuestionUpdateRequest.java
            │   │   └── QuestionController.java
            │   ├── review
            │   │   ├── request
            │   │   │   ├── ReviewDeleteRequest.java
            │   │   │   ├── ReviewRegisterRequest.java
            │   │   │   └── ReviewUpdateRequest.java
            │   │   └── ReviewController.java
            ├── model
            │   ├── entity
            │   │   ├── cart
            │   │   │   └── Cart.java
            │   │   ├── member
            │   │   │   ├── constant
            │   │   │   │   └── ROLE.java
            │   │   │   └── Member.java
            │   │   ├── order
            │   │   │   ├── constant
            │   │   │   │   └── OrderStatus.java
            │   │   │   └── Order.java
            │   │   ├── orderitem
            │   │   │   └── OrderItem.java
            │   │   ├── product
            │   │   │   ├── constant
            │   │   │   │   └── Category.java
            │   │   │   └── Product.java
            │   │   ├── question
            │   │   │   └── Question.java
            │   │   ├── review
            │   │   │   └── Review.java
            │   │   └── BaseEntity.java
            ├── repository
            │   ├── member
            │   │   ├── CartRepository.java
            │   │   └── MemberRepository.java
            │   ├── order
            │   │   └── OrderRepository.java
            │   ├── orderitem
            │   │   └── OrderItemRepository.java
            │   ├── product
            │   │   └── ProductRepository.java
            │   ├── question
            │   │   └── QuestionRepository.java
            │   └── review
            │       └── ReviewRepository.java
            ├── service
            │   ├── member
            │   │   ├── CartService.java
            │   │   └── MemberService.java
            │   ├── order
            │   │   └── OrderService.java
            │   ├── product
            │   │   └── ProductService.java
            │   ├── question
            │   │   └── QuestionService.java
            │   └── review
            │       └── ReviewService.java
            └── BackendApplication.java

```
*** 
## Appendix A. 참여자
| 이름  | Github | 비고 |
| --- | --- | --- |
| 김수민 | https://github.com/tnals2384 | --- |
| 노관태 | https://github.com/Repaion24 | --- |
| 류희수 | https://github.com/hs201016 | --- |
| 양아영 | https://github.com/ayoung-dev | --- |
| 윤건우 | https://github.com/oo-ni | --- |
| 이정우 | https://github.com/j-ra1n | 프로젝트 BaseCode 제공 |
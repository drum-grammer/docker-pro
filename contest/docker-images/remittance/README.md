# 프로젝트 컨셉
overseas remittance

# 기술 스택
- 개발 언어: java 17
- 프레임워크: springboot 3.2.2
- DB: H2
- 유틸: JPA
- 단위 테스트: junit
- 빌드 환경: gradle
- 작업 툴: intellij

# 테스트 코드
- 라이브러리:
  - junit5
  - mockito
  - assertj<br><br>

# 플랫폼 컨셉
### 주요 서비스
- 해외 송금 서비스 제공(미국, 일본)
### 이용 조건
- 회원 가입한 클라이언트만 송금 가능
### 세부 조건
- 개인 유저: 1일 송금 한도 $1000
- 법인 유저: 1일 송금 한도 $5000

# 백엔드 API
### *제약 사항!*
> - 인증 토큰은 JWT 사용: 로그인시 JWT로 인증 토큰 발급
> - Content-Type: application/json
> - 인증 토큰은 헤더에 담아서 요청
> - 민감 정보는 모두 암호화 처리 후 저장: 회원 가입시 비밀번호, 주민등록번호 or 사업자번호 암호화 -> bcrypt 처리
> - 소수점 연산 최대 자리수 12자리로 처리 -> java util 이용하여 처리

### *백엔드 API*
>- 회원가입 API
>- 로그인 API
>- 송금 견적서 요청 API => Required Authorization
>- 송금 접수 요청 API => Required Authorization
>- 회원 거래 이력 요청 API => Required Authorization
### 1. 회원가입 API
- #### EndPoint: /api/v2/user/signup
- #### Method: POST
- #### *Request Body*
  - 유저 아이디
  - 비밀 번호 -> bcrypt 처리
  - 유저 이름
  - 회원 타입 -> bcrypt 처리
```JSON
{
    "userId" : "String",
    "password" : "String",
    "name" : "String",
    "idType" : "String"
}
```
- #### *Response Body*
> 2xx
>> 200 회원가입 성공
>> ```JSON
>> {
>>    "result": {
>>    "code": 200,
>>    "message": "회원 가입 성공"
>>   }
>> }
>> ```
> 4xx
>> 400 요청 바디에 타입이 잘못된 경우
>>```JSON
>>{
>>  "result": {
>>    "code": 400,
>>    "message": "잘못된 파라미터 입니다."
>>  }
>>}
>>```

### 2. 로그인 API
- #### EndPoint: /api/v2/user/login
- #### Method: POST
- #### *Request Body*
  - 유저 아이디
  - 비밀 번호
```JSON
{
    "userId" : "String",
    "password" : "String"
}
```
- #### Response Body
>  2xx
>> 200 로그인 성공: 인증 토큰 발급
>> ```JSON
>> {
>>    "result": {
>>      "code": 200,
>>      "message": "로그인 성공"
>>    },
>>    "token": "String"
>> }
>> ```
> 4xx
>> 400 회원의 아이디와 비밀번호 일치하지 않는 경우
>>```JSON
>>{
>>  "result": {
>>    "code": 400,
>>    "message": "잘못된 파라미터 입니다."
>>  }
>>}
>>```

### 3. 송금 견적서 요청 API
- #### EndPoint: /api/v2/transfer/quote
- #### Method: GET
- #### *Parameters*
```
token(String) 인증 토큰 *required
targetCurrency(String) 통화 *required
amount(Number) 원화 금액 *required
```

### 4. 송금 접수 요청 API
- #### EndPoint: /api/v2/transfer/request
- #### Method: POST
- #### *Request Body*
    - 인증 토큰
    - 채번한 견적서의 id
```JSON
{
    "token": "String",
    "quoteId": "String"
}
```
- #### *Response Body*
>  2xx
>> 200 송금 접수 성공
>> ```JSON
>> {
>>    "result": {
>>      "code": 200,
>>      "message": "송금 접수 성공"
>>    }
>> }
>> ```
> 4xx
>> 400 견적서 만료
>>```JSON
>>{
>>  "result": {
>>    "code": 400,
>>    "message": "견적서가 만료 되었습니다."
>>  }
>>}
>>```
>> 400 1일 송금 한도 초과
>>```JSON
>>{
>>  "result": {
>>    "code": 400,
>>    "message": "오늘 송금 한도 초과 입니다."
>>  }
>>}
>>```
>> 401 유효하지 않은 인증 토큰
>>```JSON
>>{
>>  "result": {
>>    "code": 401,
>>    "message": "유효하지 않은 인증 토큰 입니다."
>>  }
>>}
>>```
> 5xx
>> 500 서버 에러
>>```JSON
>>{
>>  "result": {
>>    "code": 500,
>>    "message": "서버 에러"
>>  }
>>}
>>```

### 5. 회원 거래 이력 요청 API
- #### EndPoint: /api/v2/transfer/list
- #### Method: GET
- #### *Parameters*
```dtd
token(String) 인증 토큰 *required
```
- #### *Response Body*
>  2xx
>> 200 회원 거래 이력 응답 성공
>> - (Object) http 응답 결과
>> - (Object) 회원 거래 이력 데이터
>> ```JSON
>> {
>>  "result": {
>>      "code": 200,
>>      "message": "회원님의 송금 거래 이력입니다."
>>  },
>>  "log": {
>>      "userId": "moin@themonin.com",
>>      "name": "모인",
>>      "todayTransferCount": 2,
>>      "todayTransferUsdAmount": 278.33,
>>        "history": [
>>            {
>>                "sourceAmount": 20000,
>>                "fee": 5000,
>>                "usdExchangeRate": 1337.5,
>>                "usdAmount": 11.214953271028037,
>>                "targetCurrency": "USD",
>>                "exchangeRate": 1337.5,
>>                "targetAmount": 11.21,
>>                "userId": "test2@test.com",
>>                "requestedDate": "2024-01-26T22:41:08.994888+09:00"
>>             },
>>             {
>>                "sourceAmount": 400000,
>>                "fee": 43000,
>>                "usdExchangeRate": 1336.5,
>>                "usdAmount": 267.1156004489338,
>>                "targetCurrency": "USD",
>>                "exchangeRate": 1336.5,
>>                "targetAmount": 267.11,
>>                "userId": "test2@test.com",
>>                "requestedDate": "2024-01-26T22:43:02.401372+09:00"
>>              }
>>           ]
>>        }
>>     }
>>```
> 4xx
>> 401 유효하지 않은 인증 토큰
>>```JSON
>>{
>>  "result": {
>>    "code": 401,
>>    "message": "유효하지 않은 인증 토큰 입니다."
>>  }
>>}
>>```
# DB 테이블
### Table
> - member: 회원
> - remittance_quote: 송금 견적서
> - remittance_log : 송금 접수 요청 로그

### 설계 히스토리
> ### 세부적인 송금 견적서를 remittance_quote 테이블에 모두 저장하고 
> ### remittance_log 테이블에 견적서 아이디만 저장 해서 조회 하는 방법
>> - 이 경우 remittance_quote 테이블에 거래가 된 견적서인지 판단하는 컬럼 하나 추가 할 수 있음
>> - remittance_quote 테이블을 중심으로 송금 관련된 정보를 관리 할 수 있어서 편함
>> - 예상되는 문제점이 있다면 만료된 견적서 완료된 견적서가 뒤섞여서 테이블 하나에 데이터가 포화될 수 있음

> ### remittance_quote 테이블 remittance_log테이블 데이터를 나눠서 관리하는 방법
>> - 송금 견적서 세부정보를 송금 요청 할때 거래 이력 테이블에 한꺼번에 저장함 그 후 견적서에서 레코드 삭제
>> - 말하자면 송금 요청해서 완료된 견적서는 삭제하고 그 데이터가 그대로 거래 이력으로 이동시키는 것


### common: 로그인 기능 제외한 모든 기능은 공통으로 jwt검증
- [x] JWT 검증: JWT payload - 회원 타입, 회원 아이디

### 1. 회원가입
#### - 비즈니스 로직
- [x] DB MEMBER 테이블에 회원 저장
- [x] 하나의 유저는 개인 회원과 기업 회원으로 분류되어야 함
- [x] 개인 회원은 주민등록 번호, 법인 회원은 사업자 등록 번호로 정규식표현 패턴 valid check 로직 추가
- [x] 비밀번호 주민등록번호 사업자등록번호 암호화 처리
#### - Exception 에러 처리: false인 경우
  - [x] validation 사용해서 request body로 들어오는 필드 MethodArgumentNotValidException 처리
  - [x] 개인 회원은 주민등록 번호, 법인 회원은 사업자 등록 번호로 정규식표현 패턴 적용 -> false -> InValidPatternTypeException

### 2. 로그인
#### - 비즈니스 로직
- [x] DB MEMBER 테이블에서 아이디와 비밀번호 일치하는 회원 조회
- [ ] JWT 인증 토큰 발급 -> 인증 만료시간 = 토큰 생성시간 + 30분

### 3. 송금 견적서 요청
#### - 비즈니스 로직
- [x] 환율 정보 제공하는 외부 API로 환율정보 요청 응답
- [x] 환율 정보 제공하는 외부 API로부터 받은 응답 데이터로 수수료, 받는 금액 연산 로직 추가
  - [x] 수수료 첫째 자리 반올림 연산
  - [x] 받는 금액 defaultFractionDigits값 번째 소수점 처리 연산
  - [x] 만료 시간 연산: 만료시간 = 송금 견적서 생성시간 + 10분
- [x] 가공된 송금 견적서 데이터를 DB remittance_quote 테이블에 저장
#### - Exception 에러 처리: false인 경우
  - [x] 보내는 금액이 양의 정수 인가? true : false
  - [x] 받는 금액이 양의 정수 인가? true : false => 이 경우는 보내는 금액이 수수료보다 작으면 invalid
  - [ ] 외부 API 호출 도중 에러 발생 => Server error throw
  - [x] 외부 API 응답 데이터가 배열 json으로 응답 -> 배열길이가 0 이상인가? true : false

### 4. 송금 접수 요청
#### - 비즈니스 로직
- [x] 채번한 견적서의 id로 이전에 발행한 견적서 조회 -> DB remittance_quote 테이블에서 조회
- [x] 견적서(quote dto) + 유저아이디 + 요청 날짜 -> 이 형태로 송금 접수 내용 저장(remittance_log 테이블에 저장)
- [ ] 저장 성공시 remittance_quote 테이블에서 해당 레코드 삭제: 이 경우는 테이블 관리를 위해 삭제하는게 맞다고 주관적으로 판단 => 현재 삭제 로직을 넣진 않았습니다.  
#### - Exception 에러 처리: false인 경우
  - [x] 견적서 만료 시간 안지났는가? true : false 
  - [x] 1일 송금 금액 초과 안했는가? true : false

### 5. 회원 거래 이력 요청
#### - 비즈니스 로직
- [x] DB remittance_log 테이블에서 user_id와 일치하는 레코드들 조회
- [x] 유저의 이름 조회




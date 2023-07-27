# DNT_api

## [플러그인용 API]

### AddDomain (도메인 추가) </br>
* POST /v1/domain 

### DomainList (도메인 리스트 조회. 사전 검색) </br>
* GET /v1/domain?page=0&limit=15&search=time&lang=en

### DomainTransaction (도메인 -> 코드 변환) </br>
* GET /v1/domain/transactions?page=0&limit=10&search=일&lang=ko


## [관리자용 API]

### AddDomain (도메인 추가) </br>
* POST /v1/admin/domain

### DomainList (도메인 리스트 조회) </br>
* GET /v1/admin/domain?page=0&limit=10&search=time&lang=en&project=proj

### Domain (도메인 상세 조회) </br>
* GET /v1/admin/domain/{id}

### ProjectGroup (프로젝트명 그룹 리스트 조회) </br>
* GET /v1/admin/domain/project

### UpdateDomain (도메인 수정) </br>
* PUT /v1/admin/domain/project

### DeleteDomain (도메인 삭제) </br>
* DELETE /v1/admin/domain/{id}

### AddUser (유저 등록) </br>
* POST /v1/admin/users

### UserList (유저 리스트 조회) </br>
* GET /v1/admin/users?page =0&limit=10&name=name&email=email&security_key=skey&client_id=cid

### User (유저 상세 조회) </br>
* GET /v1/admin/users/{id}

### UpdateUser (유저 수정) </br>
* PUT /v1/admin/users

### DeleteUser (유저 삭제) </br>
* DELETE /v1/admin/users/{id}

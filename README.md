# employManagementPlatform
사원 관리 플랫폼 - EMP

## 💻 프로젝트 소개
- 사원의 정보를 관리하는 플랫폼입니다. 사원의 기본 정보, 출퇴근 기록, 사원 및 부서 정보에 대해 확인하고 수정할 수 있습니다.
- **평사원** : 출퇴근 기능, 근무 기록 확인, 내 정보 일부 수정(이메일, 휴대폰 번호, 비밀번호) 가능
- **관리자(Admin), 인사과** : '사원 추가, 사원 정보 수정, 사원 근무기록 비고란 수정, 부서 추가 및 삭제'의 추가 권한 부여

### 👪 담당 업무
- 김민철: 백엔드
- 유지아: 프론트엔드

## ⚙️ 개발 환경
- `Java`
- `HTML`
- `CSS`
- **IDE** : STS4
- **DB** : MySQL 8.0
- **ORM** : JPA
<br><br>
<br><br>

## 🔍 기능 소개
## 🔓 일반 기능
### 1. 로그인
![login](https://github.com/user-attachments/assets/843fe3af-2ece-479d-8f7a-15a10c8af989)
<br><br>

### 2. 출퇴근 기능
- 사원은 로그인하여 헤더의 '출근', '퇴근' 버튼을 통해 출근과 퇴근을 기록함.
- DB : **매일 1회 0시 0분 30초**에 모든 사원의 근무 기록을 '결근'으로 추가, 현재 상태를 '비출근'으로 변경하여 초기화함
<br><br>
- **로그인 초기 상태**: DB에 의해 '결근' 상태, 출근 버튼 활성화 상태 <br><br>
![initial](https://github.com/user-attachments/assets/84ca11e5-739b-4def-bc60-6c181e15fe96)
<br><br><br>
- **출근 상태**: '출근' 버튼을 누르면, '출근' 상태로 변경됨 + 퇴근 버튼 활성화 <br><br>
![출근](https://github.com/user-attachments/assets/74b25b90-c8ba-496d-a780-23fe1d98fbd1)
<br><br><br>
- **퇴근 상태**: '퇴근' 버튼을 누르면, '퇴근' 상태로 변경됨 + 출근 버튼 비활성화(0시 0분 30초에 활성화) <br><br>
![퇴근](https://github.com/user-attachments/assets/99e183a8-813d-4b23-9acf-315a366aed95)
<br><br><br>

### 3. 내 정보 수정
- **이메일, 휴대폰 번호, 비밀번호**만 수정 가능함.
- 나머지 정보 변경시, **인사과에 문의**하여 인사과 직원이 사원 정보 수정 기능으로 수정해야함.
<br><br>
![수정(my)](https://github.com/user-attachments/assets/03f248a2-49a4-4a00-9377-0f0ae104dcc0)
<br><br>
![pwd](https://github.com/user-attachments/assets/92cd6e7b-43e1-4669-aa68-dd05a2a3a8da)
<br><br><br>

## 🔒 권한 기능
### 4. 사원 추가 및 정보 수정
- 새로운 사원 정보를 입력하여, **사원을 추가**할 수 있음. <br><br>
![추가](https://github.com/user-attachments/assets/585e788a-02c5-41fc-9585-1391b763e182)
<br><br><br>
- **사원 목록** <br><br>
![목록](https://github.com/user-attachments/assets/a3ee45b0-955f-4ddf-85c1-c370b9f24b23)
<br><br><br>
- **근무 기록 비고란 수정** <br><br>
![비고](https://github.com/user-attachments/assets/e7b778f9-e2ad-439a-8eab-75481ef89c02)
<br><br><br>
- **사원 정보 수정** <br><br>
![수정](https://github.com/user-attachments/assets/17717927-46b5-4893-857d-5e9b1b4a0f31)
<br><br><br>

### 4. 사원 추가 및 삭제
- **부서**를 추가하거나 삭제함. <br><br>
![부서](https://github.com/user-attachments/assets/0e29f80d-5bfa-4751-89db-00c9de071048)
<br><br><br>

## 💾 DB
- 기본 데이터셋: /json(data)
- SQL: /sql

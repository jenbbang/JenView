<p align="center">
<img src="https://postfiles.pstatic.net/MjAyNDA3MThfMTQy/MDAxNzIxMjk2MzY4NjMw.PdpD0S4A2VNOGz8el2oXHkYHfqGPCl6cBk96pIce4S4g.CWLBze_8RI8SZiXxZlNDdb9o1Gx3kn_8kgzlNAy5LuQg.PNG/Jen_View.png?type=w773">
</p>

# 🎬 JenView 🎬
#### 📅 2024년 6월 ~ 2024년 7월



## 📢 개요
JenView는 대용량 데이터를 처리하며 동영상과 광고를 관리하는 시스템입니다. </br>
동영상 및 광고 시청 기록을 바탕으로 통계를 생성하고, 매일 자정에 Spring Batch를 사용하여 통계 및 정산 작업을 수행합니다 </br>


## 💥 주요 기능
  
| 기능 영역 | 세부 기능 |
| --- | --- |
| 고객 포털 | 👤 **회원 가입** <br> 🔐 **로그인** <br> 🚪 **로그아웃** |
| 동영상 및 광고 관리 | 📹 **동영상 관리**: 등록, 재생, 정지 <br> 📢 **광고 관리**: 등록, 배치 |
| 시청 기록 관리 | 📈 **동영상 시청 기록 관리** <br> 📊 **광고 시청 기록 관리** |
| 통계 생성 및 정산 | 📊 **동영상 및 광고 통계 생성**: 일간/주간/월간 Top 5 (조회수, 재생시간) <br> ⏰ **정산 작업**: 매일 자정에 Spring Batch를 사용하여 통계 및 정산 작업 수행 |




## 🛠️ 기술 스택
![Java](https://img.shields.io/badge/Language-Java%2021-007396?style=flat-square&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/DB-MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white)
![Gradle](https://img.shields.io/badge/Build%20Tool-Gradle-02303A?style=flat-square&logo=gradle&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Framework-Spring%20Boot%203.3.0-6DB33F?style=flat-square&logo=spring-boot&logoColor=white)
![Docker](https://img.shields.io/badge/Containerization-Docker%20/%20Docker%20Compose-2496ED?style=flat-square&logo=docker&logoColor=white)
![Spring Batch](https://img.shields.io/badge/Batch%20Processing-Spring%20Batch%205.0-6DB33F?style=flat-square&logo=spring&logoColor=white)
![JWT](https://img.shields.io/badge/Authentication-JWT%20(JSON%20Web%20Token)-000000?style=flat-square&logo=json-web-tokens&logoColor=white)

## 아키텍처
프로젝트의 전체 구조와 구성 요소 간의 상호작용을 나타내는 아키텍처 다이어그램입니다.
<p align="left">

</p>

## ERD (Entity-Relationship Diagram)
<details>
<summary>데이터베이스 테이블과 그 관계를 보여주는 ERD입니다.</summary>
<p align="left">
<img src="https://postfiles.pstatic.net/MjAyNDA3MjBfMTEz/MDAxNzIxNDE3MTI1ODk2.cFyETvF3vw2N2n2poNLmPrnGTZkZtaUn501t20LnY7Ag.78qjFwY7zXyNz9KpnFPk9U6kSQAk5lC1eIExU14MALwg.PNG/image.png?type=w773">
</p>
</details>


## 📄 프로젝트 하이라이트

### 1. 대용량 데이터 처리 성능 개선 (99.92% 향상)

#### 1.1 최종 성능 결과  
- 1천만 데이터 기준 결과 : 3 m 49 s 139 ms

#### 1.2 성능 개선
  
| 단계 | 데이터  | 처리시간 | 개선율 |
| --- | --- | --- | --- |
| Test 코드 개선 전 | 1천만 건 | 약 83시간 20분 | 0.00% ↑ |
| Test 코드 개선 후 | 1천만 건 | 약 22시간 30분 | 73.00% ↓ |
| Union 쿼리  | 1천만 건 | 3 m 49 s 139 ms | 99.92% ↓ |

#### 1.3 개선 내용 

- **문제점**
    - 성능 문제: 대용량 데이터 삽입 시 비효율 발생
    - 트랜잭션 오버헤드: 개별 `save` 호출로 인한 오버헤드

- **해결 방법**
    - **배치 처리**: 데이터베이스 삽입을 배치 단위로 처리
    - **트랜잭션 최적화**: 트랜잭션 오버헤드 최소화

- **결과**
    - **성능 향상**: 데이터베이스 통신 횟수 감소로 성능 향상
    - **가독성 증가**: 코드 분리로 가독성 향상

- **쿼리 사용 이유**
    - **테스트 시간 단축:** 유니온 쿼리 사용으로 테스트 시간이 83시간 20분에서 3분 49초로 단축.


## 🔥 트러블 슈팅 

## 💭 기술적 의사 결정 

## ⚙️ 프로젝트 설정
| 설정 영역 | 세부 내용 |
| --- | --- |
| 🐳 Docker 설정 | Dockerfile을 작성하여 애플리케이션 실행 환경 정의 <br> Docker Compose를 통해 애플리케이션 및 데이터베이스 컨테이너 관리 |
| 🌱 Spring Boot 설정 | Spring Initializr를 사용하여 프로젝트 생성 <br> 필요한 의존성을 Gradle에 추가 |
| ⏲️ Spring Batch 설정 | Spring Batch 관련 설정 추가하여 배치 작업 설정 |
| 🔐 JWT 설정 | JWT를 사용한 인증 및 권한 부여 설정 |
| 🗄️ 데이터베이스 설정 | 애플리케이션 프로퍼티 파일을 통해 데이터베이스 연결 설정 |
| 🌐 HTTP Request / Response | REST 컨트롤러 작성하여 클라이언트와 서버 간 통신 처리 |

## ⚙️ 설치 및 실행 방법
<details>

<summary>설치 및 실행 방법</summary>

### 사전 요구 사항
- JDK 21
- Gradle
- Docker 및 Docker Compose

### 설치
1. 프로젝트를 클론합니다:
    ```bash
    git clone https://github.com/yourusername/JenView.git
    cd JenView
    ```

2. 의존성을 설치합니다:
    ```bash
    ./gradlew clean build
    ```

3. Docker 컨테이너를 실행합니다:
    ```bash
    docker-compose up -d
    ```

4. 애플리케이션을 실행합니다:
    ```bash
    ./gradlew bootRun
    ```

### 데이터베이스 설정
기본적으로 MySQL, Postgres, MongoDB 등 다양한 데이터베이스를 지원합니다. 데이터베이스 연결 설정은 `application.properties` 파일을 통해 구성합니다.

### 테스트 데이터 생성
프로젝트에 포함된 테스트 코드를 실행하여 테스트 데이터를 생성할 수 있습니다:
```bash
./gradlew test
</details>





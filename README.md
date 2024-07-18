<p align="center">
<img src="https://postfiles.pstatic.net/MjAyNDA3MThfMTQy/MDAxNzIxMjk2MzY4NjMw.PdpD0S4A2VNOGz8el2oXHkYHfqGPCl6cBk96pIce4S4g.CWLBze_8RI8SZiXxZlNDdb9o1Gx3kn_8kgzlNAy5LuQg.PNG/Jen_View.png?type=w773">
</p>

# JenView 프로젝트

## 개요
JenView는 동영상과 광고를 관리하고, 동영상 및 광고 시청 기록을 바탕으로 통계를 생성하는 시스템입니다. </br>
이 프로젝트는 Spring Boot를 사용하여 구현되었으며, 동영상 및 광고의 재생 기록을 관리하고, 매일 자정에 통계 및 정산 작업을 수행합니다.

## 주요 기능
- 동영상 및 광고 관리
- 동영상 및 광고 시청 기록 관리
- 동영상 및 광고 통계 생성
- 매일 자정에 통계 및 정산 작업 수행

## 기술 스택
- **언어**: Java 21 이상
- **DB**: MySQL, Postgres, MongoDB 등 무관
- **빌드 도구**: Gradle
- **프레임워크**: Spring Boot 3.XX
- **컨테이너화 도구**: Docker / Docker Compose
- **배치 처리**: Spring Batch
- **인증 방식**: JWT (JSON Web Token)
- **통신 방식**: HTTP Request / Response

## 프로젝트 설정
### Docker 설정
- **Dockerfile**을 작성하여 애플리케이션 실행 환경 정의
- **Docker Compose**를 통해 애플리케이션 및 데이터베이스 컨테이너 관리

### Spring Boot 설정
- **Spring Initializr**를 사용하여 프로젝트 생성
- 필요한 의존성을 **Gradle**에 추가

### Spring Batch 설정
- Spring Batch 관련 설정 추가하여 배치 작업 설정

### JWT 설정
- JWT를 사용한 인증 및 권한 부여 설정

### 데이터베이스 설정
- 애플리케이션 프로퍼티 파일을 통해 데이터베이스 연결 설정

### HTTP Request / Response
- REST 컨트롤러 작성하여 클라이언트와 서버 간 통신 처리

## 설치 및 실행 방법

### 사전 요구 사항
- JDK 21 이상
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
```

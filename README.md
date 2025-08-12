# Backend Project

## 1. 프로젝트 소개

본 프로젝트는 Java 21, Spring Boot 3를 기반으로 구축된 백엔드 서버입니다. 제품 품질 대시보드, 상품 관리, 사용자 인증 등 다양한 기능을 API로 제공합니다. MongoDB를 주 데이터베이스로 사용하며, Docker를 통해 Nginx, Spring Boot 애플리케이션, 데이터베이스를 함께 관리하여 배포 및 운영의 편의성을 높였습니다.

## 2. 주요 기능

- **사용자 인증**: 관리자 계정 로그인 및 세션 관리 기능을 제공합니다.
- **대시보드**: 제품 품질 현황, 불량률, 균일도 등 다양한 통계 데이터를 조회할 수 있는 API를 제공합니다.
- **상품 관리**: 상품 목록 및 개별 상품의 품질 정보를 조회하고 관리하는 기능을 제공합니다.
- **클라우드 연동**: Google Cloud Storage(GCS)와 연동하여 관련 데이터를 처리합니다.

## 3. 사용 기술

- **언어**: `Java 21`
- **프레임워크**: `Spring Boot 3.4.3`, `Spring Security`
- **데이터베이스**: `MongoDB`
- **API 문서**: `SpringDoc (Swagger-UI)`
- **인프라**: `Docker`, `Nginx`
- **기타**: `Google Cloud Storage`, `Lombok`

## 4. 시작하기

### 4.1. 사전 요구 사항

- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/)
- Google Cloud Storage 서비스 계정 키 파일

### 4.2. 실행 방법

1.  **프로젝트 클론**
    ```bash
    git clone https://github.com/your-repository/backend.git
    cd backend
    ```

2.  **환경 변수 설정**
    프로젝트 루트 디렉터리에 `.env` 파일을 생성하고 아래 내용을 채워넣습니다. `docker-compose.yml`에서 이 값을 참조하여 컨테이너 환경 변수를 설정합니다.

    ```env
    # MongoDB 설정
    MONGO_INITDB_ROOT_USERNAME=your_mongo_user
    MONGO_INITDB_ROOT_PASSWORD=your_mongo_password
    MONGO_DB_NAME=your_db_name

    # Google Cloud Storage 서비스 계정 키 경로 (절대 경로 또는 상대 경로)
    GCS_SERVICE_ACCOUNT_PATH=/path/to/your/service-account.json
    ```

3.  **애플리케이션 빌드**
    아래 명령어를 실행하여 Spring Boot 애플리케이션을 빌드합니다.
    ```bash
    ./gradlew build
    ```
    > **참고**: Windows에서는 `./gradlew.bat build`를 사용하세요.

4.  **Docker Compose 실행**
    아래 명령어를 사용하여 Docker 컨테이너를 실행합니다.
    ```bash
    docker-compose up -d
    ```
    이제 Nginx가 80번 포트에서 요청을 받아 Spring Boot 애플리케이션으로 전달합니다.

## 5. API 문서

애플리케이션이 실행되면, 아래 URL을 통해 Swagger API 문서를 확인할 수 있습니다.

- **Swagger UI**: `https://api.zezeone-sf.site/swagger-ui/index.html`

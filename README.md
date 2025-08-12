# Spring Boot Backend API Server

[![Java](https://img.shields.io/badge/Java-21-red.svg)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.3-green.svg)](https://spring.io/projects/spring-boot)
[![MongoDB](https://img.shields.io/badge/MongoDB-6.0-4EA94B.svg)](https://www.mongodb.com/)
[![Docker](https://img.shields.io/badge/Docker-v3.8-blue.svg)](https://www.docker.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

---

## 프로젝트 개요

본 프로젝트는 Java 21과 Spring Boot 3.4.3을 기반으로 구축된 백엔드 API 서버입니다. 사용자 인증, 제품 품질 대시보드, 상품 관리 등 다양한 기능을 API 형태로 제공합니다. MongoDB를 주 데이터베이스로 사용하며, Docker와 Nginx를 활용하여 컨테이너 기반의 효율적인 배포 및 운영 환경을 지원합니다.

## ✨ 주요 기능

-   **사용자 인증**: 관리자 계정 로그인 및 세션 관리 기능을 제공합니다.
-   **대시보드**: 제품 품질 현황, 불량률, 균일도 등 다양한 통계 데이터를 조회할 수 있는 API를 제공합니다.
-   **상품 관리**: 상품 목록 및 개별 상품의 품질 정보를 조회하고 관리하는 기능을 제공합니다.
-   **클라우드 연동**: Google Cloud Storage(GCS)와 연동하여 관련 데이터를 처리합니다.
-   **API 문서**: SpringDoc (Swagger-UI)를 통해 자동으로 생성되는 API 문서를 제공합니다.
-   **컨테이너 기반 배포**: Docker를 사용하여 애플리케이션을 패키징하고 Docker Compose로 오케스트레이션합니다.
-   **CI/CD 자동화**: GitHub Actions를 통해 코드 변경 시 자동으로 빌드 및 배포 워크플로우를 수행합니다.

## ️ 기술 스택

| 구분 | 기술 | 버전/설명 |
| --- | --- | --- |
| **언어** | Java | 21 |
| **웹 프레임워크** | Spring Boot | 3.4.3 |
| **보안** | Spring Security | |
| **데이터베이스** | MongoDB | |
| **클라우드** | Google Cloud Storage (GCS) | |
| **API 문서** | SpringDoc (Swagger-UI) | |
| **컨테이너** | Docker | |
| **빌드 도구** | Gradle | |
| **CI/CD** | GitHub Actions | |

## API 엔드포인트

애플리케이션이 실행되면, 아래 URL을 통해 Swagger API 문서를 확인할 수 있습니다.

-   **Swagger UI**: `https://api.zezeone-sf.site/swagger-ui/index.html`

주요 API 카테고리는 다음과 같습니다:

-   **인증 (Authentication)**: 로그인, 로그아웃
-   **대시보드 (Dashboard)**: 불량률, 제품 통계, 균일도
-   **상품 (Product)**: 상품 목록, 상품 품질 상세
-   **유틸리티 (Utilities)**: 일반 유틸리티 기능

## ⚙️ CI/CD

`.github/workflows/cicd.yml` 에 정의된 GitHub Actions 워크플로우는 `main` 브랜치에 코드가 푸시될 때마다 다음 작업을 자동으로 수행합니다.

1.  애플리케이션의 Docker 이미지를 빌드합니다.
2.  빌드된 이미지를 **Docker Hub**와 같은 컨테이너 레지스트리에 푸시합니다.
3.  Google Cloud Platform (GCP)과 같은 클라우드 환경의 배포 서버에 접속하여 최신 이미지를 pull 받고, 기존 컨테이너를 중지/제거한 후 새 버전의 컨테이너를 실행하여 자동 배포합니다.

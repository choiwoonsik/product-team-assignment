# KCD 제품팀 POS 카드 매출 데이터 연동

## 개요

> POS 카드 매출 데이터 연동 시스템 프로젝트.

## 기술

- `Spring Boot`
- `Spring Batch`
- `Kafka-Consumer`

## 버전

- `Java 17`
- `Kotlin 1.9.23`
- `Spring Boot 3.2.5`

## 프로젝트 세팅 방법

1. 도커 컨테이너 실행 (mysql, kafka)
```bash
cd tools
docker-compose up -d
```

2. queryDsl 설정
```bash
./gradlew kaptKotlin
```

3. DB, Test DB 설정 flyway 순서대로 실행
```bash
./gradlew flywayClean
./gradlew flywayMigrate
./gradlew flywayCleanTestDB
./gradlew flywayMigrateTestDB
```

- 단위/통합 테스트 실행
```bash
./gradlew test
```

## 사용 기술 스택

- 개발 환경
    - `intelliJ`, `Git/Github`
- 개발 언어
    - `Kotlin`
- 프레임워크
    - `Spring Boot`, `Spring Batch`
- DB
    - `MySQL`
- API
    - `REST API`, `Swagger`
- Message Queue
    - `Kafka`
- 라이브러리
    - `QueryDsl`
- Test
    - `JUnit5`, `MockK`
- 빌드
    - `Gradle`, `Docker`

## 개발 문서
- [기술 문서](https://www.notion.so/woonsik/KCD-ac9fc94904b74c518d69ee30028ca808)
- [Swagger API 문서](http://localhost:8080/swagger-ui.html)

## 테이블 구조
<img width="1492" alt="스크린샷 2024-05-22 오전 4 11 41" src="https://github.com/choiwoonsik/seat_reserve/assets/42247724/600e07da-05a3-4217-86ee-417a31bae31f">
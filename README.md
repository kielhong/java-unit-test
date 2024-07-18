# java-unit-test
 패스트 캠퍼스 Java 단위 테스트 프로젝트

---
**목차**

1. 패키지 구조
2. 테스트
    1. 네이밍
    2. 테스트 구조
    3. 픽스쳐
3. 개별 예제     
---

## 1. 패키지 구조

패키지 구조는 [헥사고날 아키텍처][hexagonal-architecture]를 따르고 다음과 같습니다.
 
```text
article
    ├── domain
    │    ├── Article.java 
    │    └── Board.java   
    ├── application
    │    ├── service
    │    │   └── ArticleService.java
    │    └── port
    │        ├── in
    │        │   ├── CreateArticleUseCase.java
    │        │   ├── DeleteArticleUseCase.java
    │        │   ├── GetArticleUseCase.java
    │        │   └── ModifyArticleUseCase.java
    │        └── out
    │            ├── CommandArticlePort.java
    │            ├── LoadArticlePort.java
    │            └── LoadBoardPort.java
    └── adapter
             ├── in
             │    └── ArticleController.java
             └── out
                 └── persistence
                       ├── ArticlePersistenceAdapter.java
                       ├── BoardPersistenceAdapter.java
                       ├── entity
                       │    ├── ArticleJpaEntity.java
                       │    └── BoardJpaEntity.java
                       └── repository
                            ├── ArticleRepository.java
                            └── BoardRepository.java
```

- domain
   - 도메인 객체(Article, Board)
- application
   - service
      - 인커밍 포트의 인터페이스들을 구현하는 서비스 클래스
    - port
      - in
         - 인커밍 어댑터(이 프로젝트에서는 Controller)가 의존하는 인커밍 포트가 위치
         - 서비스가 구현해야 하는 인터페이스 
      - out
         - 아웃고잉 어댑터 인터페이스
- adapter
   - in
      - 애플리케이션 계층의 인커밍 포트를 호출하는 인커밍 어댑터가 위치
         - 외부의 API 요청을 받아 처리하는 컨트롤러(이 프로젝트에서는 ArticleController)
   - out
      - 아웃고잉 포트에 대한 구현을 제공하는 아웃고잉 어댑터가 위치
         - JPA 관련 영속성을 처리하는 Entity, JpaRepostory가 위치

## 2. 테스트

테스트를 작성하는 일반적인 규칙에 대해 설명합니다.

### 2.1. 네이밍

단위 테스트 클래스는 `[SUT]Test`로 명명합니다.

`SUT`는 테스트 대상 클래스명입니다.

e.g. `ArticleServiceTest`, `ArticleControllerTest`, etc.

### 2.2. 테스트 클래스 위치

단위 테스트 클래스는 main 코드에 대응되는 package에 생성한다
e.g. `com.example.demo.article.application.service.ArticleService.java` -> `com.example.demo.article.application.service.ArticleServiceTest.java`

### 2.3 테스트 픽스쳐

테스트 수행 전에 사용하는 환경 설정 혹은 데이터

Java Test Fixture 설정
```
plugins {
    id 'java-test-fixtures'
}
```
https://github.com/kielhong/java-unit-test/tree/main/src/testFixtures

## 3. 개별 예제
### Junit + Mockito로 서비스 객체 테스트
- [Junit 예시](https://github.com/kielhong/java-unit-test/blob/main/src/test/java/com/example/demo/article/domain/Ch02Clip01JunitTest.java)
- [AssertJ 예시](https://github.com/kielhong/java-unit-test/blob/main/src/test/java/com/example/demo/article/domain/Ch02Clip01JunitAssertJTest.java)
- [Junit + Mockito 테스트 기본](https://github.com/kielhong/java-unit-test/blob/main/src/test/java/com/example/demo/article/application/service/Ch02Clip02JunitMockitoTest.java)
- [Exception Assert](https://github.com/kielhong/java-unit-test/blob/main/src/test/java/com/example/demo/article/application/service/Ch02Clip03ExceptionTest.java)
- [Paramterized Test](https://github.com/kielhong/java-unit-test/blob/main/src/test/java/com/example/demo/article/application/service/Ch02Clip04ParameterizedTest.java)
- [Nested Test](https://github.com/kielhong/java-unit-test/blob/main/src/test/java/com/example/demo/article/application/service/Ch02Clip05NestedTest.java)

### 컨트롤러 테스트
- [순수한 단위 테스트 형태의 컨트롤러 테스트](https://github.com/kielhong/java-unit-test/blob/main/src/test/java/com/example/demo/article/adapter/in/api/CH03Clip01ArticleControllerUnitTest.java)
- [@WebMvcTest를 이용한 컨트롤러 테스트](https://github.com/kielhong/java-unit-test/blob/main/src/test/java/com/example/demo/article/adapter/in/api/CH03Clip02WebMvcTest.java)
- [jsonPath 를 이용한 response 검증](https://github.com/kielhong/java-unit-test/blob/main/src/test/java/com/example/demo/article/adapter/in/api/CH03Clip03JsonPathAssertTest.java)

### Repository 테스트
- [@DataJpaTest를 이용한 Repository 테스트] (https://github.com/kielhong/java-unit-test/blob/main/src/test/java/com/example/demo/article/adapter/out/persistence/repository/Ch4Clip01ArticleRepositoryTest.java)
- [Sql로 테스트 데이터 생성한 테스트](https://github.com/kielhong/java-unit-test/blob/main/src/test/java/com/example/demo/article/adapter/out/persistence/repository/Ch4Clip02ArticleRepositoryFixtureTest.java)

### 통합 테스트
- [@SpringBootTest를 이용한 통합 테스트](https://github.com/kielhong/java-unit-test/blob/main/src/integrationTest/java/com/example/demo/article/in/api/ArticleControllerIntTest.java)


## 참고 사이트
- Junit5: https://junit.org/junit5/
- Assertj : https://assertj.github.io/doc/
- Mockito : https://github.com/mockito/mockito
- Hamcrest : https://hamcrest.org/
- jsonpath : https://github.com/json-path/JsonPath
- java-test-fixture : https://docs.gradle.org/current/userguide/java_testing.html#sec:java_test_fixtures

[hexagonal-architecture]: https://alistair.cockburn.us/hexagonal-architecture/

# java-unit-test
 패스트 캠퍼스 Java 단위 테스트 프로젝트

---
**목차**

1. 패키지 구조
2. 테스트
    1. 네이밍
    2. 테스트 구조
    3. 테스트 작성에 관한 일반 가이드
    4. 픽스쳐
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

### i. 네이밍

단위 테스트 클래스는 `[SUT]Test`로 명명합니다.

`SUT`는 테스트 대상 클래스명입니다.

e.g. `ArticleServiceTest`, `ArticleControllerTest`, etc.

### ii. 테스트 클래스 위치

단위 테스트 클래스는 main 코드에 대응되는 package에 생성한다
e.g. `com.example.demo.article.application.service.ArticleService.java` -> `com.example.demo.article.application.service.ArticleServiceTest.java`

## 3. 참고 사이트
- Junit5: https://junit.org/junit5/
- Assertj : https://assertj.github.io/doc/
- Mockito : https://github.com/mockito/mockito
- Hamcrest : https://hamcrest.org/
- jsonpath : https://github.com/json-path/JsonPath
- java-test-fixture : https://docs.gradle.org/current/userguide/java_testing.html#sec:java_test_fixtures

[hexagonal-architecture]: https://alistair.cockburn.us/hexagonal-architecture/

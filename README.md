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

패키지 구조는 [헥사고날 아키텍처][hexagonal-architecture]를 따릅니다. 다음과 같은 구조를 형성합니다.
 
```text
aggregate
    ├── domain           # 도메인
    ├── application
    │    ├── service     # 제공하는 애플리케이션 서비스
    │    └── port
    │        ├── in      # 애플리케이션 서비스가 구현하는 인터페이스
    │        └── out     # 애플리케이션 서비스가 의존하는 인터페이스
    └── adapter
             ├── in      # incoming adapter 또는 애플리케이션 서비스를 의존하는 어댑터
             └── out     # outgoing port의 구현체
```

위 구조의 패키지는 하위 패키지를 가질 수 있으며, 패키지 구분에 대한 기준은 없습니다.

## 2. 테스트

테스트를 작성하는 일반적인 규칙에 대해 설명합니다.

### i. 네이밍

단위 테스트 클래스는 `[SUT]Test`로 명명합니다.

`SUT`는 테스트 대상 클래스명입니다.

e.g. `ArticleServiceTest`, `ArticleControllerTest`, etc.






[hexagonal-architecture]: https://alistair.cockburn.us/hexagonal-architecture/
[junit]: https://junit.org/junit5/
[assertj]: https://assertj.github.io/doc/
[mockito]: https://github.com/mockito/mockito
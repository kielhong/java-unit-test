package com.example.demo.article.application.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.example.demo.article.adapter.in.api.dto.ArticleDto;
import com.example.demo.article.application.port.out.CommandArticlePort;
import com.example.demo.article.application.port.out.LoadArticlePort;
import com.example.demo.article.application.port.out.LoadBoardPort;
import com.example.demo.article.domain.ArticleFixtures;
import com.example.demo.article.domain.BoardFixtures;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class Ch02Clip04ParameterizedTest {
    private ArticleService sut;

    @Mock
    private LoadArticlePort loadArticlePort;
    @Mock
    private CommandArticlePort commandArticlePort;
    @Mock
    private LoadBoardPort loadBoardPort;

    @BeforeEach
    void setUp() {
        sut = new ArticleService(loadArticlePort, commandArticlePort, loadBoardPort);
    }

    @ParameterizedTest
    @ValueSource(strings = {"english", "한글", "!@#$"})
    @DisplayName("영어, 한글, 특수문자로 제목 생성")
    void valueSource_throwIllegalArgumentException(String subject) {
        var request = new ArticleDto.CreateArticleRequest(5L, subject, "content", "user");
        var board = BoardFixtures.board();
        given(loadBoardPort.findBoardById(any()))
            .willReturn(Optional.of(board));
        var createdArticle = ArticleFixtures.article();
        given(commandArticlePort.createArticle(any()))
            .willReturn(createdArticle);

        var result = sut.createArticle(request);

        then(result)
            .isEqualTo(createdArticle);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void isEmpty_throwIllegalArgumentException(String subject) {
        var request = new ArticleDto.CreateArticleRequest(5L, subject, "content", "user");

        thenThrownBy(() -> sut.createArticle(request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest(name = "{0} + {1} = {2}")
    @CsvSource(
        value = {
            "2,3,5",
            "1,5,6",
            "10,11,21"
        }
    )
    @DisplayName("2개의 합")
    void csvSource_addNumber(Integer a, Integer b, Integer sum) {
        then(sum)
            .isEqualTo(a + b);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidParameters")
    @DisplayName("정상적이지 않은 param 이면 IllegalArgumentException")
    void methodSource_throwIllegalArgumentException(String name, String subject, String content, String username) {
        var request = new ArticleDto.CreateArticleRequest(5L, subject, content, username);

        thenThrownBy(() -> sut.createArticle(request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> invalidParameters() {
        return Stream.of(
            Arguments.of("subject is null", null, "content", "user"),
            Arguments.of("subject is empty", "", "content", "user"),
            Arguments.of("content is null", "subject", null, "user"),
            Arguments.of("content is empty", "subject", "", "user"),
            Arguments.of("username is null", "subject", "content", null)
        );
    }
}

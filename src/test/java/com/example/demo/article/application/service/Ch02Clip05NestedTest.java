package com.example.demo.article.application.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.example.demo.article.application.port.out.CommandArticlePort;
import com.example.demo.article.application.port.out.LoadArticlePort;
import com.example.demo.article.application.port.out.LoadBoardPort;
import com.example.demo.article.domain.ArticleFixtures;
import com.example.demo.common.exception.ResourceNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class Ch02Clip05NestedTest {
    private ArticleService sut;

    private LoadArticlePort loadArticlePort;
    private CommandArticlePort commandArticlePort;
    private LoadBoardPort loadBoardPort;

    @BeforeEach
    void setUp() {
        loadArticlePort = Mockito.mock(LoadArticlePort.class);
        commandArticlePort = Mockito.mock(CommandArticlePort.class);
        loadBoardPort = Mockito.mock(LoadBoardPort.class);

        sut = new ArticleService(loadArticlePort, commandArticlePort, loadBoardPort);
    }

    @Test
    @DisplayName("articleId 로 조회시 Article 반환")
    void return_Article() {
        // given
        var article = ArticleFixtures.article();
        given(loadArticlePort.findArticleById(Mockito.anyLong()))
            .willReturn(Optional.of(article));

        // when
        var result = sut.getArticleById(1L);

        // then
        then(result)
            .isNotNull()
            .hasNoNullFieldsOrProperties()
            .hasFieldOrPropertyWithValue("id", article.getId())
            .hasFieldOrPropertyWithValue("board.id", article.getBoard().getId())
            .hasFieldOrPropertyWithValue("subject", article.getSubject())
            .hasFieldOrPropertyWithValue("content", article.getContent())
            .hasFieldOrPropertyWithValue("username", article.getUsername())
            .hasFieldOrProperty("createdAt");
    }

    @Test
    @DisplayName("Article 존재하지 않을 경우 ResourceNotFoundException throw")
    void throw_ResourceNotFoundException() {
        given(loadArticlePort.findArticleById(any()))
            .willReturn(Optional.empty());

        thenThrownBy(() -> sut.getArticleById(1L))
            .isInstanceOf(ResourceNotFoundException.class);
    }

    @Nested
    @DisplayName("Article 조회")
    class GetArticle {
        @Test
        @DisplayName("articleId 로 조회시 Article 반환")
        void return_Article() {
            var article = ArticleFixtures.article();
            given(loadArticlePort.findArticleById(any()))
                .willReturn(Optional.of(article));

            var result = sut.getArticleById(1L);

            then(result)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", article.getId())
                .hasFieldOrPropertyWithValue("board.id", article.getBoard().getId())
                .hasFieldOrPropertyWithValue("subject", article.getSubject())
                .hasFieldOrPropertyWithValue("content", article.getContent())
                .hasFieldOrPropertyWithValue("username", article.getUsername())
                .hasFieldOrPropertyWithValue("createdAt", article.getCreatedAt());
        }

        @Test
        @DisplayName("존재하지 않을 경우 NoSuchElementException throw")
        void throw_NoSuchElementException() {
            given(loadArticlePort.findArticleById(any()))
                .willReturn(Optional.empty());

            thenThrownBy(() -> sut.getArticleById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("id : 1 게시물이 없습니다");
        }
    }
}

package com.example.demo.article.application.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

import com.example.demo.article.domain.ArticleFixtures;
import com.example.demo.article.application.port.in.dto.ArticleRequest;
import com.example.demo.article.application.port.out.CommandArticlePort;
import com.example.demo.article.application.port.out.LoadArticlePort;
import com.example.demo.article.domain.Article;
import com.example.demo.article.domain.Board;
import com.example.demo.common.exception.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ArticleServiceTest {
    private ArticleService sut;

    private LoadArticlePort loadArticlePort;
    private CommandArticlePort commandArticlePort;

    @BeforeEach
    void setUp() {
        loadArticlePort = Mockito.mock(LoadArticlePort.class);
        commandArticlePort = Mockito.mock(CommandArticlePort.class);

        sut = new ArticleService(loadArticlePort, commandArticlePort);
    }

    @Nested
    @DisplayName("Article 한 개 조회")
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
                .isInstanceOf(NoSuchElementException.class);
        }
    }

    @Test
    @DisplayName("Board의 Article 목록 조회")
    void getArticlesByBoard_listArticles() {
        var article1 = ArticleFixtures.article(1L);
        var article2 = ArticleFixtures.article(2L);
        given(loadArticlePort.findArticlesByBoardId(any()))
            .willReturn(List.of(article1, article2));

        var result = sut.getArticlesByBoard(5L);

        then(result)
            .hasSize(2)
            .extracting("board.id").containsOnly(5L);
    }

    @Test
    @DisplayName("Article 생성하면 생성된 Article 반환")
    void createArticle_returnCreatedArticleId() {
        var request = new ArticleRequest(null, 5L, "subject", "content", "user");
        var article = ArticleFixtures.article();
        given(commandArticlePort.createArticle(any()))
            .willReturn(article);

        var result = sut.postArticle(request);

        then(result)
            .isEqualTo(article);
    }

    @Nested
    @DisplayName("Article 변경")
    class ModifyArticle {
        private ArticleRequest request;

        @BeforeEach
        void setUp() {
            request = new ArticleRequest(6L, 6L, "new subject", "new content", "otheruser");
        }

        @Test
        @DisplayName("변경된 Article 반환")
        void returnModifiedArticleId() {
            var article = ArticleFixtures.article();
            var board = new Board(6L, "other board");
            request = new ArticleRequest(6L, 6L, "new subject", "new content", article.getUsername());

            given(loadArticlePort.findArticleById(any()))
                .willReturn(Optional.of(article));
            var modifiedArticle = new Article(1L, board, "new subject", "new content", article.getUsername(), LocalDateTime.now());
            given(commandArticlePort.modifyArticle(any()))
                .willReturn(modifiedArticle);

            var result = sut.modifyArticle(request);

            then(result)
                .isEqualTo(modifiedArticle);
        }

        @Test
        @DisplayName("존재하지 않는 Article 이면 NoSuchElementException throw")
        void notExistArticle_throwNoSuchElementException() {
            given(loadArticlePort.findArticleById(any()))
                .willReturn(Optional.empty());

            thenThrownBy(() -> sut.modifyArticle(request))
                .isInstanceOf(NoSuchElementException.class);
        }

        @Test
        @DisplayName("user 가 다르면 AccessDeniedException throw")
        void otherUser_throwException() {
            var article = ArticleFixtures.article();
            given(loadArticlePort.findArticleById(any()))
                .willReturn(Optional.of(article));

            thenThrownBy(() -> sut.modifyArticle(request))
                .isInstanceOf(AccessDeniedException.class);
        }
    }

    @Test
    @DisplayName("Article 삭제")
    void deleteArticle() {
        willDoNothing()
            .given(commandArticlePort).deleteArticle(any());

        sut.deleteArticle(1L);

        verify(commandArticlePort).deleteArticle(1L);
    }
}
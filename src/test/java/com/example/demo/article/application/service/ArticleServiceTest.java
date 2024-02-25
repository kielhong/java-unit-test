package com.example.demo.article.application.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.example.demo.article.application.port.out.LoadArticlePort;
import com.example.demo.article.domain.Article;
import com.example.demo.article.domain.Board;
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

    @BeforeEach
    void setUp() {
        loadArticlePort = Mockito.mock(LoadArticlePort.class);

        sut = new ArticleService(loadArticlePort);
    }

    @Nested
    @DisplayName("Article 한개 조회")
    class GetArticle{
        @Test
        @DisplayName("Article Entity 조회 되면 article 반환")
        void getById_find_article_returnArticle() {
            var board = new Board(5L, "board");
            var article = new Article(1L, board, "", "", "", LocalDateTime.now());
            given(loadArticlePort.findArticleById(any()))
                .willReturn(Optional.of(article));


            var result = sut.getById(1L);

            then(result)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", article.getId())
                .hasFieldOrPropertyWithValue("board.id", board.getId())
                .hasFieldOrPropertyWithValue("subject", article.getSubject())
                .hasFieldOrPropertyWithValue("content", article.getContent())
                .hasFieldOrPropertyWithValue("username", article.getUsername())
                .hasFieldOrPropertyWithValue("createdAt", article.getCreatedAt());
        }

        @Test
        @DisplayName("Article Entity 조회 되지 않으면 NoSuchElementException throw")
        void getById_find_no_Article_throwNoSuchElementException() {
            given(loadArticlePort.findArticleById(any()))
                .willReturn(Optional.empty());

            thenThrownBy(() -> sut.getById(1L))
                .isInstanceOf(NoSuchElementException.class);
        }
    }

    @Test
    @DisplayName("같은 Board의 Article 목록 조회")
    void getArticlesByBoard_listArticles() {
        var board = new Board(5L, "board");
        var article1 = new Article(1L, board, "article1", "", "", LocalDateTime.now());
        var article2 = new Article(2L, board, "article2", "", "", LocalDateTime.now());
        given(loadArticlePort.findArticlesByBoardId(any()))
            .willReturn(List.of(article1, article2));

        var result = sut.getArticlesByBoard(5L);

        then(result)
            .hasSize(2)
            .extracting("board.id").containsOnly(5L);
    }
}
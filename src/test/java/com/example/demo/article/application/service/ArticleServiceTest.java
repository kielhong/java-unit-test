package com.example.demo.article.application.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.example.demo.article.ArticleFixtures;
import com.example.demo.article.application.port.in.dto.ArticleRequest;
import com.example.demo.article.application.port.out.CommandArticlePort;
import com.example.demo.article.application.port.out.LoadArticlePort;
import com.example.demo.article.domain.Article;
import com.example.demo.article.domain.Board;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

    @Test
    @DisplayName("articleId로 Article 한개 조회")
    void given_articleId_when_getById_then_return_Article() {
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
    @DisplayName("boardId로 같은 Board의 Article 목록 조회")
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
    @DisplayName("Article 생성")
    void createArticle_returnCreatedArticleId() {
        var request = new ArticleRequest(5L, "subject", "content", "user");
        var article = ArticleFixtures.article();
        given(commandArticlePort.createArticle(any()))
            .willReturn(article);

        var result = sut.postArticle(request);

        then(result)
            .isEqualTo(article);
    }

    @Test
    @DisplayName("Article 변경")
    void modifyArticle_returnModifiedArticleId() {
        var request = new ArticleRequest(6L, "new subject", "new content", "user");
        var board = new Board(6L, "board6");
        var modifiedArticle = new Article(1L, board, "new subject", "new content", "user", LocalDateTime.now());
        given(commandArticlePort.modifyArticle(any()))
            .willReturn(modifiedArticle);

        var result = sut.modifyArticle(request);

        then(result)
            .isEqualTo(modifiedArticle);
    }
}
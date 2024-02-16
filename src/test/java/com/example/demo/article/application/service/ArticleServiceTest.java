package com.example.demo.article.application.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.example.demo.article.application.port.out.LoadArticlePort;
import com.example.demo.article.domain.Article;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ArticleServiceTest {

    private ArticleService service;

    private final LoadArticlePort loadArticlePort = Mockito.mock(LoadArticlePort.class);

    @BeforeEach
    void setUp() {
        service = new ArticleService(loadArticlePort);
    }

    @Test
    @DisplayName("articleId로 Article 한개 조회")
    void given_articleId_when_getById_then_return_Article() {
        Long articleId = 1L;
        var article = new Article(articleId, 10L, "", "", "", LocalDateTime.now());
        given(loadArticlePort.findArticleById(any()))
            .willReturn(Optional.of(article));

        var result = service.getById(articleId);

        then(result)
            .isNotNull()
            .hasFieldOrPropertyWithValue("id", article.getId())
            .hasFieldOrPropertyWithValue("boardId", article.getBoardId())
            .hasFieldOrPropertyWithValue("subject", article.getSubject())
            .hasFieldOrPropertyWithValue("content", article.getContent())
            .hasFieldOrPropertyWithValue("username", article.getUsername())
            .hasFieldOrPropertyWithValue("createdAt", article.getCreatedAt());
    }
}
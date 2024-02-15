package com.example.demo.article.application.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.example.demo.article.adapter.out.persistence.entity.ArticleJpaEntity;
import com.example.demo.article.application.port.out.LoadArticlePort;
import com.example.demo.article.domain.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ArticleServiceTest {

    private ArticleService service;

    private LoadArticlePort loadArticlePort;

    @BeforeEach
    void setUp() {
        loadArticlePort = Mockito.mock(LoadArticlePort.class);
        service = new ArticleService(loadArticlePort);
    }

    @Test
    @DisplayName("articleId로 Article 한개 조회")
    void given_articleId_when_findArticleById_then_return_an_Article() {
        Long articleId = 1L;
        given(loadArticlePort.findArticleById(any()))
            .willReturn(new ArticleJpaEntity());

        Article result = service.findArticle(articleId);

        then(result)
            .isNotNull();
    }
}
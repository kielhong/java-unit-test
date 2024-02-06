package com.example.demo.article.application.service;

import com.example.demo.article.application.ArticleService;
import com.example.demo.article.application.port.out.LoadArticlePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    private ArticleService service;

    private LoadArticlePort loadArticlePort;

    @BeforeEach
    void setUp() {
        loadArticlePort = Mockito.mock(loadArticlePort);
        service = new ArticleService(loadArticlePort);
    }

    @Test
    @DisplayName("articleId로 Article 한개 조회")
    void given_articleId_when_findArticleById_then_return_an_Article() {

    }
}
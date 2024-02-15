package com.example.demo.article.application.service;

import com.example.demo.article.application.port.in.GetArticleUseCase;
import com.example.demo.article.application.port.out.LoadArticlePort;
import com.example.demo.article.domain.Article;

public class ArticleService implements GetArticleUseCase {
    final LoadArticlePort loadArticlePort;

    public ArticleService(LoadArticlePort loadArticlePort) {
        this.loadArticlePort = loadArticlePort;
    }

    @Override
    public Article findArticle(Long articleId) {
        loadArticlePort.findArticleById(articleId);
        return new Article();
    }
}

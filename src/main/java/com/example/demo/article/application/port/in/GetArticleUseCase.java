package com.example.demo.article.application.port.in;

import com.example.demo.article.domain.Article;

public interface GetArticleUseCase {
    Article findArticle(Long articleId);
}

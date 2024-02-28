package com.example.demo.article.application.port.in;

import com.example.demo.article.application.port.in.dto.ArticleRequest;
import com.example.demo.article.domain.Article;

public interface CreateArticleUseCase {
    Article createArticle(ArticleRequest request);
}

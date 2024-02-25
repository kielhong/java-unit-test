package com.example.demo.article.application.port.in;

import com.example.demo.article.application.port.in.dto.ArticleResponse;

public interface GetArticleUseCase {
    ArticleResponse getById(Long articleId);
}

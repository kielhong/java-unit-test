package com.example.demo.article.application.port.in;

import com.example.demo.article.adapter.in.api.dto.ArticleDto;
import com.example.demo.article.domain.Article;

public interface CreateArticleUseCase {
    Article createArticle(ArticleDto.CreateArticleRequest request);
}

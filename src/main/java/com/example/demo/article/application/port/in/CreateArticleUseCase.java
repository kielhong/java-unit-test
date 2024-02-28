package com.example.demo.article.application.port.in;

import com.example.demo.article.adapter.in.web.dto.ArticleDto;
import com.example.demo.article.domain.Article;

public interface CreateArticleUseCase {
    Article createArticle(ArticleDto.CreateArticleRequest request);
}

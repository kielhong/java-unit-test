package com.example.demo.article.application.port.out;

import com.example.demo.article.application.port.in.dto.ArticleRequest;
import com.example.demo.article.domain.Article;

public interface CommandArticlePort {
    Article createArticle(ArticleRequest request);

    Article modifyArticle(ArticleRequest request);
}

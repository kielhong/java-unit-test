package com.example.demo.article.application.port.out;

import com.example.demo.article.adapter.out.persistence.entity.ArticleJpaEntity;

public class LoadArticlePort {

    public ArticleJpaEntity findArticleById(Long articleId) {
        return new ArticleJpaEntity();
    }
}

package com.example.demo.article.adapter.in.web;

import com.example.demo.article.application.port.in.GetArticleUseCase;
import com.example.demo.article.application.port.in.dto.ArticleResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleController {
    private final GetArticleUseCase getArticleUseCase;

    public ArticleController(GetArticleUseCase getArticleUseCase) {
        this.getArticleUseCase = getArticleUseCase;
    }

    @RequestMapping("/articles/{articleId}")
    ArticleResponse getArticle(@PathVariable Long articleId) {
        var article = getArticleUseCase.getById(articleId);

        return ArticleResponse.from(article);
    }
}

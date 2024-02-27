package com.example.demo.article.adapter.in.web;

import com.example.demo.article.application.port.in.GetArticleUseCase;
import com.example.demo.article.application.port.in.dto.ArticleResponse;
import java.util.Collections;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("articles")
public class ArticleController {
    private final GetArticleUseCase getArticleUseCase;

    public ArticleController(GetArticleUseCase getArticleUseCase) {
        this.getArticleUseCase = getArticleUseCase;
    }

    @RequestMapping("{articleId}")
    ArticleResponse getArticle(@PathVariable Long articleId) {
        var article = getArticleUseCase.getArticleById(articleId);

        return ArticleResponse.from(article);
    }

    @RequestMapping(params = "boardId")
    List<ArticleResponse> listArticlesByBoard(@RequestParam Long boardId) {
        return getArticleUseCase.getArticlesByBoard(boardId).stream()
            .map(ArticleResponse::from)
            .toList();
    }
}

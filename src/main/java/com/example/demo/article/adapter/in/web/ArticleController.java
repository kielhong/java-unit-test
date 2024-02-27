package com.example.demo.article.adapter.in.web;

import com.example.demo.article.application.port.in.DeleteArticleUseCase;
import com.example.demo.article.application.port.in.GetArticleUseCase;
import com.example.demo.article.application.port.in.dto.ArticleResponse;
import java.util.Collections;
import java.util.List;
import org.hibernate.sql.Delete;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("articles")
public class ArticleController {
    private final GetArticleUseCase getArticleUseCase;
    private final DeleteArticleUseCase deleteArticleUseCase;

    public ArticleController(GetArticleUseCase getArticleUseCase, DeleteArticleUseCase deleteArticleUseCase) {
        this.getArticleUseCase = getArticleUseCase;
        this.deleteArticleUseCase = deleteArticleUseCase;
    }

    @GetMapping("{articleId}")
    ArticleResponse getArticle(@PathVariable Long articleId) {
        var article = getArticleUseCase.getArticleById(articleId);

        return ArticleResponse.from(article);
    }

    @GetMapping(params = "boardId")
    List<ArticleResponse> listArticlesByBoard(@RequestParam Long boardId) {
        return getArticleUseCase.getArticlesByBoard(boardId).stream()
            .map(ArticleResponse::from)
            .toList();
    }

    @DeleteMapping("{articleId}")
    void deleteArticled(@PathVariable Long articleId) {
        deleteArticleUseCase.deleteArticle(articleId);
    }
}

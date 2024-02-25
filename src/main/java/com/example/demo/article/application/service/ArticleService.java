package com.example.demo.article.application.service;

import com.example.demo.article.application.port.in.dto.ArticleResponse;
import com.example.demo.article.application.port.in.GetArticleUseCase;
import com.example.demo.article.application.port.out.LoadArticlePort;
import com.example.demo.article.domain.Article;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ArticleService implements GetArticleUseCase {
    private final LoadArticlePort loadArticlePort;

    public ArticleService(LoadArticlePort loadArticlePort) {
        this.loadArticlePort = loadArticlePort;
    }

    @Override
    public ArticleResponse getById(Long articleId) {
        Optional<Article> article = loadArticlePort.findArticleById(articleId);
        return ArticleResponse.from(article.get());
    }

    @Override
    public List<ArticleResponse> getArticlesByBoard(Long boardId) {
        return loadArticlePort.findArticlesByBoardId(boardId).stream()
            .map(ArticleResponse::from)
            .collect(Collectors.toList());
    }
}

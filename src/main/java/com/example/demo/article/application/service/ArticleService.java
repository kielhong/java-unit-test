package com.example.demo.article.application.service;

import com.example.demo.article.application.port.in.dto.ArticleResponse;
import com.example.demo.article.application.port.in.GetArticleUseCase;
import com.example.demo.article.application.port.out.LoadArticlePort;
import com.example.demo.article.domain.Article;
import java.util.Optional;
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
}

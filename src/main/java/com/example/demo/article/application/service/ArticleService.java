package com.example.demo.article.application.service;

import com.example.demo.article.application.port.in.GetArticleUseCase;
import com.example.demo.article.application.port.out.LoadArticlePort;
import com.example.demo.article.domain.Article;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ArticleService implements GetArticleUseCase {
    private final LoadArticlePort loadArticlePort;

    public ArticleService(LoadArticlePort loadArticlePort) {
        this.loadArticlePort = loadArticlePort;
    }

    @Override
    public Article getArticleById(Long articleId) {
        return loadArticlePort.findArticleById(articleId)
            .orElseThrow();
    }

    @Override
    public List<Article> getArticlesByBoard(Long boardId) {
        return loadArticlePort.findArticlesByBoardId(boardId);
    }
}

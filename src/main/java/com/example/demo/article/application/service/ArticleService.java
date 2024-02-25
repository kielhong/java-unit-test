package com.example.demo.article.application.service;

import com.example.demo.article.application.port.in.GetArticleUseCase;
import com.example.demo.article.application.port.in.ModifyArticleUseCase;
import com.example.demo.article.application.port.in.PostArticleUseCase;
import com.example.demo.article.application.port.in.dto.ArticleRequest;
import com.example.demo.article.application.port.out.CommandArticlePort;
import com.example.demo.article.application.port.out.LoadArticlePort;
import com.example.demo.article.domain.Article;
import com.example.demo.common.exception.AccessDeniedException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ArticleService implements GetArticleUseCase, PostArticleUseCase, ModifyArticleUseCase {
    private final LoadArticlePort loadArticlePort;
    private final CommandArticlePort commandArticlePort;

    public ArticleService(LoadArticlePort loadArticlePort, CommandArticlePort commandArticlePort) {
        this.loadArticlePort = loadArticlePort;
        this.commandArticlePort = commandArticlePort;
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

    @Override
    public Article postArticle(ArticleRequest request) {
        return commandArticlePort.createArticle(request);
    }

    @Override
    public Article modifyArticle(ArticleRequest request) {
        Article article = loadArticlePort.findArticleById(request.id())
            .orElseThrow();

        if (!article.getUsername().equals(request.username())) {
            throw new AccessDeniedException("");
        }
        return commandArticlePort.modifyArticle(request);
    }
}

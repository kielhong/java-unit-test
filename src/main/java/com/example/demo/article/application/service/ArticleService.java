package com.example.demo.article.application.service;

import com.example.demo.article.adapter.in.api.dto.ArticleDto;
import com.example.demo.article.application.port.in.CreateArticleUseCase;
import com.example.demo.article.application.port.in.DeleteArticleUseCase;
import com.example.demo.article.application.port.in.ModifyArticleUseCase;
import com.example.demo.article.application.port.out.CommandArticlePort;
import com.example.demo.article.application.port.out.LoadArticlePort;
import com.example.demo.article.application.port.out.LoadBoardPort;
import com.example.demo.article.application.port.out.LoadUserPort;
import com.example.demo.article.domain.Article;
import com.example.demo.common.exception.AccessDeniedException;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ArticleService implements CreateArticleUseCase, ModifyArticleUseCase, DeleteArticleUseCase {
    private final LoadArticlePort loadArticlePort;
    private final CommandArticlePort commandArticlePort;
    private final LoadBoardPort loadBoardPort;
    private final LoadUserPort loadUserPort;

    public ArticleService(LoadArticlePort loadArticlePort, CommandArticlePort commandArticlePort, LoadBoardPort loadBoardPort, LoadUserPort loadUserPort) {
        this.loadArticlePort = loadArticlePort;
        this.commandArticlePort = commandArticlePort;
        this.loadBoardPort = loadBoardPort;
        this.loadUserPort = loadUserPort;
    }

    @Override
    public Article createArticle(ArticleDto.CreateArticleRequest request) {
        if (!StringUtils.hasLength(request.subject()) || !StringUtils.hasLength(request.content()) || request.username() == null) {
            throw new IllegalArgumentException("invalid param");
        }

        if (!loadUserPort.existsUser(request.username())) {
            throw new AccessDeniedException(request.username() + " not exists");
        }

        var board = loadBoardPort.findBoardById(request.boardId())
            .orElseThrow();



        var article = new Article(null, board, request.subject(), request.content(), request.username(), LocalDateTime.now());
        return commandArticlePort.createArticle(article);
    }

    @Override
    public Article modifyArticle(ArticleDto.UpdateArticleRequest request) {
        Article article = loadArticlePort.findArticleById(request.id())
            .orElseThrow();

        if (!article.getUsername().equals(request.username())) {
            throw new AccessDeniedException("");
        }

        article.update(request);
        return commandArticlePort.modifyArticle(article);
    }

    @Override
    public void deleteArticle(Long articleId) {
        commandArticlePort.deleteArticle(articleId);
    }
}

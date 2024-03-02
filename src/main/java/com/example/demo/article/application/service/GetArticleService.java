package com.example.demo.article.application.service;

import com.example.demo.article.application.port.in.GetArticleUseCase;
import com.example.demo.article.application.port.out.LoadArticlePort;
import com.example.demo.article.domain.Article;
import com.example.demo.common.exception.ResourceNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetArticleService implements GetArticleUseCase {
    private final LoadArticlePort loadArticlePort;

    public GetArticleService(LoadArticlePort loadArticlePort) {
        this.loadArticlePort = loadArticlePort;
    }

    @Override
    public Article getArticleById(Long articleId) {
        return loadArticlePort.findArticleById(articleId)
            .orElseThrow(() -> new ResourceNotFoundException("id : " + articleId + " 게시물이 없습니다"));
    }

    @Override
    public List<Article> getArticlesByBoard(Long boardId) {
        return loadArticlePort.findArticlesByBoardId(boardId);
    }
}

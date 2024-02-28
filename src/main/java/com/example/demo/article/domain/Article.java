package com.example.demo.article.domain;

import com.example.demo.article.adapter.in.api.dto.ArticleDto;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class Article {
    private Long id;
    private Board board;
    private String subject;
    private String content;
    private String username;
    private LocalDateTime createdAt;

    public Article(Long id, Board board, String subject, String content, String username, LocalDateTime createdAt) {
        this.id = id;
        this.board = board;
        this.subject = subject;
        this.content = content;
        this.username = username;
        this.createdAt = createdAt;
    }

    public void update(ArticleDto.UpdateArticleRequest request) {
        this.subject = request.subject();
        this.content = request.content();
    }
}

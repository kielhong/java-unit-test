package com.example.demo.article.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
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

    public void update(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }
}

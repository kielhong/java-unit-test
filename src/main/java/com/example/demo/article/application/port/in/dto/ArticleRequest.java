package com.example.demo.article.application.port.in.dto;

import com.example.demo.article.domain.Article;
import java.time.LocalDateTime;

public record ArticleRequest(
    Long id,
    BoardRequest boardRequest,
    String subject,
    String content,
    String username
) {
    public Article toDomain() {
        return new Article(id, boardRequest.toDomain(), subject, content, username, LocalDateTime.now());
    }
}
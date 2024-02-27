package com.example.demo.article.application.port.in.dto;

import com.example.demo.article.domain.Article;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ArticleRequest(
    Long id,
    @NotNull
    BoardRequest board,
    @NotNull
    String subject,
    @NotNull
    String content,
    @NotEmpty
    String username
) {
    public Article toDomain() {
        return new Article(id, board.toDomain(), subject, content, username, LocalDateTime.now());
    }
}
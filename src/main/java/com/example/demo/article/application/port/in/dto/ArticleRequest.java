package com.example.demo.article.application.port.in.dto;

import com.example.demo.article.domain.Article;
import com.example.demo.article.domain.Board;
import java.time.LocalDateTime;

public record ArticleRequest(
    Long id,
    Long boardId,
    String subject,
    String content,
    String username
) {
    public Article toDomain() {
        return new Article(id, new Board(boardId, ""), subject, content, username, LocalDateTime.now());
    }
}
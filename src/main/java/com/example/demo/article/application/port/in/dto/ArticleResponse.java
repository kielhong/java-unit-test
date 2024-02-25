package com.example.demo.article.application.port.in.dto;

import com.example.demo.article.domain.Article;
import com.example.demo.article.domain.Board;
import java.time.LocalDateTime;

public record ArticleResponse(
    Long id,
    Board board,
    String subject,
    String content,
    String username,
    LocalDateTime createdAt
) {
    public static ArticleResponse from(Article article) {
        return new ArticleResponse(
            article.getId(),
            article.getBoard(),
            article.getSubject(),
            article.getContent(),
            article.getUsername(),
            article.getCreatedAt()
        );
    }
}

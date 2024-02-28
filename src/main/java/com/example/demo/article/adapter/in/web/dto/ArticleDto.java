package com.example.demo.article.adapter.in.web.dto;

import com.example.demo.article.application.port.in.dto.BoardRequest;
import com.example.demo.article.domain.Article;
import com.example.demo.article.domain.Board;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ArticleDto {
    public record CreateArticleRequest(
        @NotNull
        Long boardId,
        @NotNull
        String subject,
        @NotNull
        String content,
        @NotEmpty
        String username
    ) {
        public Article toDomain(Board board) {
            return new Article(null, board, subject, content, username, LocalDateTime.now());
        }
    }

    public static record UpdateArticleRequest(
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
}

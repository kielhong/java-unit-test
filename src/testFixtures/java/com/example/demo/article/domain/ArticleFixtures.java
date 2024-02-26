package com.example.demo.article.domain;

import java.time.LocalDateTime;

public class ArticleFixtures {
    public static Article article(Long id) {
        var board = new Board(5L, "board");

        return new Article(id, board, "subject" + id, "content" + id, "user" + id,
            LocalDateTime.parse("2024-02-01T10:20:30").plusDays(id));
    }

    public static Article article() {
        var board = new Board(5L, "board");

        return new Article(1L, board, "subject", "content", "user",
            LocalDateTime.parse("2024-02-01T10:20:30"));
    }
}

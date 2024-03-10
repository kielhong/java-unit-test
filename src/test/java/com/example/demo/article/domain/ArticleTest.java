package com.example.demo.article.domain;

import static org.assertj.core.api.BDDAssertions.then;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArticleTest {
    @Test
    @DisplayName("Article 생성")
    void constructArticle() {
        var board = new Board(5L, "board");

        var article = Article.builder()
            .id(1L)
            .board(board)
            .subject("subject")
            .content("content")
            .username("user")
            .createdAt(LocalDateTime.now())
            .build();

        then(article)
            .isNotNull()
            .hasNoNullFieldsOrProperties()
            .hasFieldOrPropertyWithValue("id", 1L)
            .hasFieldOrPropertyWithValue("board.id", 5L)
            .hasFieldOrPropertyWithValue("subject", "subject")
            .hasFieldOrPropertyWithValue("content", "content")
            .hasFieldOrProperty("createdAt");
    }

    @Test
    @DisplayName("Article update")
    void updateArticle() {
        // Given
        var article = Article.builder()
            .id(1L)
            .board(new Board(5L, "board"))
            .subject("subject")
            .content("content")
            .username("user")
            .createdAt(LocalDateTime.now())
            .build();

        // When
        article.update("new subject", "new content");

        // Assert
        then(article)
            .isNotNull()
            .hasNoNullFieldsOrProperties()
            .hasFieldOrPropertyWithValue("id", 1L)
            .hasFieldOrPropertyWithValue("subject", "new subject")
            .hasFieldOrPropertyWithValue("content", "new content");
    }
}

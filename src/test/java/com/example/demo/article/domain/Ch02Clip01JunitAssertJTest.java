package com.example.demo.article.domain;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Ch02Clip01JunitAssertJTest {
    private Article article;

    @BeforeEach
    void setUp() {
        // Arrange, Given
        article = Article.builder()
            .id(1L)
            .board(new Board(5L, "board"))
            .subject("subject")
            .content("content")
            .username("user")
            .createdAt(LocalDateTime.now())
            .build();
    }

    @Test
    @DisplayName("updateArticle - AssertJ Assertion")
    void updateArticle() {
        // Act
        article.update("new subject", "new content");

        // Assert
        Assertions.assertThat(article.getId())
            .isNotNull()
            .isEqualTo(1L)
            .isGreaterThan(0L);
        Assertions.assertThat(article)
            .hasNoNullFieldsOrProperties()
            .hasFieldOrPropertyWithValue("id", 1L)
            .hasFieldOrPropertyWithValue("board.id", 5L)
            .hasFieldOrPropertyWithValue("subject", "new subject")
            .hasFieldOrPropertyWithValue("content", "new content")
            .hasFieldOrProperty("createdAt");
    }

    @Test
    @DisplayName("updateArticle - BDD Style Assertion")
    void updateArticle_BDDStyle() {
        // When
        article.update("new subject", "new content");

        // Then
        BDDAssertions.then(article)
            .hasNoNullFieldsOrProperties()
            .hasFieldOrPropertyWithValue("id", 1L)
            .hasFieldOrPropertyWithValue("board.id", 5L)
            .hasFieldOrPropertyWithValue("subject", "new subject")
            .hasFieldOrPropertyWithValue("content", "new content")
            .hasFieldOrProperty("createdAt");
    }
}

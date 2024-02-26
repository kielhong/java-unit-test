package com.example.demo.article.out.persistence;

import com.example.demo.article.adapter.out.persistence.entity.ArticleJpaEntity;
import com.example.demo.article.adapter.out.persistence.entity.BoardJpaEntity;
import java.time.LocalDateTime;

public class ArticleJpaEntityFixtures {
    private ArticleJpaEntityFixtures() {}

    public static ArticleJpaEntity entity(Long index) {
        var boardJpaEntity = new BoardJpaEntity("board");

        return new ArticleJpaEntity(boardJpaEntity, "subject" + index, "content" + index,
            "username" + index, LocalDateTime.parse("2023-02-10T11:12:33").plusDays(index));
    }

    public static ArticleJpaEntity entity() {
        var boardJpaEntity = new BoardJpaEntity("board");

        return new ArticleJpaEntity(boardJpaEntity, "subject", "content", "username",
            LocalDateTime.parse("2023-02-10T11:12:33"));
    }
}

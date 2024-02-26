package com.example.demo.article.out.persistence;

import com.example.demo.article.adapter.out.persistence.entity.BoardJpaEntity;
import org.springframework.test.util.ReflectionTestUtils;

public class BoardJpaEntityFixtures {
    public static BoardJpaEntity board() {
        var boardJpaEntity = new BoardJpaEntity("board");
        ReflectionTestUtils.setField(boardJpaEntity, "id", 5L);

        return boardJpaEntity;
    }
}

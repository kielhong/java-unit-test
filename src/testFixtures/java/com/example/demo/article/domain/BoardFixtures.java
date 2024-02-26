package com.example.demo.article.domain;

import java.time.LocalDateTime;

public class BoardFixtures {
    public static Board board() {
        return new Board(5L, "board");
    }
}

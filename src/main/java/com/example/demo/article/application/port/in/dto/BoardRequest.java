package com.example.demo.article.application.port.in.dto;

import com.example.demo.article.domain.Board;

public record BoardRequest(
    Long id,
    String name
) {

    public Board toDomain() {
        return new Board(id, name);
    }
}

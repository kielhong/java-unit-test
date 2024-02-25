package com.example.demo.article.application.port.in.dto;

import com.example.demo.article.domain.Board;

public record BoardResponse(
    Long id,
    String name
) {
    public static BoardResponse from(Board board) {
        return new BoardResponse(
            board.getId(),
            board.getName()
        );
    }
}

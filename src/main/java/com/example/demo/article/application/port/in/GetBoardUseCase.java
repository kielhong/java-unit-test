package com.example.demo.article.application.port.in;

import com.example.demo.article.domain.Board;

public interface GetBoardUseCase {
    Board getBoardById(Long boardId);
}

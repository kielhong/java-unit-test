package com.example.demo.article.application.port.out;

import com.example.demo.article.domain.Board;
import java.util.Optional;

public interface LoadBoardPort {
    Optional<Board> findBoardById(Long boardId);
}

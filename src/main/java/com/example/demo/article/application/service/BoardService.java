package com.example.demo.article.application.service;

import com.example.demo.article.adapter.out.persistence.BoardPersistenceAdapter;
import com.example.demo.article.application.port.in.GetBoardUseCase;
import com.example.demo.article.domain.Board;
import org.springframework.stereotype.Service;

@Service
public class BoardService implements GetBoardUseCase {
    private BoardPersistenceAdapter boardPersistenceAdapter;

    public BoardService(BoardPersistenceAdapter boardPersistenceAdapter) {
        this.boardPersistenceAdapter = boardPersistenceAdapter;
    }

    @Override
    public Board getBoardById(Long boardId) {
        return boardPersistenceAdapter.findBoardById(boardId)
            .orElseThrow();
    }
}

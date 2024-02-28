package com.example.demo.article.adapter.out.persistence;

import com.example.demo.article.adapter.out.persistence.entity.BoardJpaEntity;
import com.example.demo.article.adapter.out.persistence.repository.BoardRepository;
import com.example.demo.article.application.port.out.LoadBoardPort;
import com.example.demo.article.domain.Board;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class BoardPersistenceAdapter implements LoadBoardPort {
    private BoardRepository boardRepository;

    public BoardPersistenceAdapter(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public Optional<Board> findBoardById(Long boardId) {
        return boardRepository.findById(boardId)
            .map(BoardJpaEntity::toDomain);
    }
}

package com.example.demo.article.adapter.out.persistence;

import com.example.demo.article.application.port.out.LoadBoardPort;
import com.example.demo.article.domain.Board;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class BoardPersistenceAdapter implements LoadBoardPort {
    @Override
    public Optional<Board> findBoardById(Long boardId) {
        return Optional.empty();
    }
}

package com.example.demo.article.adapter.out.persistence;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.example.demo.article.adapter.out.persistence.repository.BoardRepository;
import com.example.demo.article.domain.Board;
import com.example.demo.article.out.persistence.BoardJpaEntityFixtures;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BoardPersistenceAdapterTest {
    private BoardPersistenceAdapter adapter;

    private BoardRepository boardRepository;

    @BeforeEach
    void setUp() {
        boardRepository = Mockito.mock(BoardRepository.class);

        adapter = new BoardPersistenceAdapter(boardRepository);
    }

    @Test
    void findBoardById() {
        var boardJpaEntity = BoardJpaEntityFixtures.board();
        given(boardRepository.findById(any()))
            .willReturn(Optional.of(boardJpaEntity));

        var result = adapter.findBoardById(5L);

        then(result)
            .isPresent()
            .hasValueSatisfying(board ->
                then(board)
                    .isInstanceOf(Board.class)
                    .hasFieldOrPropertyWithValue("id", 5L)
            );
    }
}
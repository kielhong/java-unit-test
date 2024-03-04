package com.example.demo.article.application.service;

import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.example.demo.article.adapter.in.api.dto.ArticleDto;
import com.example.demo.article.application.port.out.CommandArticlePort;
import com.example.demo.article.application.port.out.LoadArticlePort;
import com.example.demo.article.application.port.out.LoadBoardPort;
import com.example.demo.article.application.port.out.LoadUserPort;
import com.example.demo.article.domain.Board;
import com.example.demo.article.domain.BoardFixtures;
import com.example.demo.common.exception.AccessDeniedException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class Ch02Clip03ExceptionTest {
    private ArticleService sut;

    @Mock
    private LoadArticlePort loadArticlePort;
    @Mock
    private CommandArticlePort commandArticlePort;
    @Mock(strictness = Mock.Strictness.LENIENT)
    private LoadBoardPort loadBoardPort;
    @Mock
    private LoadUserPort loadUserPort;

    private final Board board = BoardFixtures.board();

    @BeforeEach
    void setUp() {
        sut = new ArticleService(loadArticlePort, commandArticlePort, loadBoardPort, loadUserPort);
    }

    @Test
    @DisplayName("subject가 정상적이지 않으면 IllegalArgumentException")
    void throwIllegalArgumentException() {
        var request = new ArticleDto.CreateArticleRequest(5L, null, "content", "user");
        given(loadBoardPort.findBoardById(any()))
            .willReturn(Optional.of(board));

        thenThrownBy(() -> sut.createArticle(request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("subject should not empty");
    }

    @Test
    @DisplayName("존재하지 않는 작성자이면 AccessDeniedException")
    void throwAccessDeniedException() {
        var request = new ArticleDto.CreateArticleRequest(5L, "subject", "content", "nouser");
        given(loadUserPort.existsUser(any()))
            .willReturn(false);

        thenThrownBy(() -> sut.createArticle(request))
            .isInstanceOf(AccessDeniedException.class);
    }
}

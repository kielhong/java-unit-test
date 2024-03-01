package com.example.demo.article.application.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

import com.example.demo.article.adapter.in.api.dto.ArticleDto;
import com.example.demo.article.adapter.in.api.dto.BoardDto;
import com.example.demo.article.application.port.out.CommandArticlePort;
import com.example.demo.article.application.port.out.LoadArticlePort;
import com.example.demo.article.application.port.out.LoadBoardPort;
import com.example.demo.article.application.port.out.LoadUserPort;
import com.example.demo.article.domain.Article;
import com.example.demo.article.domain.ArticleFixtures;
import com.example.demo.article.domain.Board;
import com.example.demo.article.domain.BoardFixtures;
import com.example.demo.common.exception.AccessDeniedException;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ArticleServiceTest {
    private ArticleService sut;

    private LoadArticlePort loadArticlePort;
    private CommandArticlePort commandArticlePort;
    private LoadBoardPort loadBoardPort;
    private LoadUserPort loadUserPort;

    @BeforeEach
    void setUp() {
        loadArticlePort = Mockito.mock(LoadArticlePort.class);
        commandArticlePort = Mockito.mock(CommandArticlePort.class);
        loadBoardPort = Mockito.mock(LoadBoardPort.class);
        loadUserPort = Mockito.mock(LoadUserPort.class);

        sut = new ArticleService(loadArticlePort, commandArticlePort, loadBoardPort, loadUserPort);
    }

    @Nested
    @DisplayName("Article 생성")
    class PostArticle {
        private final ArticleDto.CreateArticleRequest request = new ArticleDto.CreateArticleRequest(5L, "subject", "content", "user");

        @Test
        @DisplayName("생성된 Article 반환")
        void returnCreatedArticleId() {
            var board = BoardFixtures.board();
            given(loadBoardPort.findBoardById(any()))
                .willReturn(Optional.of(board));
            given(loadUserPort.existsUser(any()))
                .willReturn(true);
            var createdArticle = ArticleFixtures.article();
            given(commandArticlePort.createArticle(any()))
                .willReturn(createdArticle);

            var result = sut.createArticle(request);

            then(result)
                .isEqualTo(createdArticle);
        }

        @Test
        @DisplayName("존재하지 않는 작성자이면 AccessDeniedException")
        void throwAccessDeniedException() {
            var board = BoardFixtures.board();
            given(loadBoardPort.findBoardById(any()))
                .willReturn(Optional.of(board));
            given(loadUserPort.existsUser(any()))
                .willReturn(false);

            thenThrownBy(() -> sut.createArticle(request))
                .isInstanceOf(AccessDeniedException.class);
        }
    }


    @Nested
    @DisplayName("Article 변경")
    class ModifyArticle {
        private ArticleDto.UpdateArticleRequest request;

        @BeforeEach
        void setUp() {
            request = new ArticleDto.UpdateArticleRequest(6L, new BoardDto(6L, "board"), "new subject", "new content", "user");
        }

        @Test
        @DisplayName("변경된 Article 반환")
        void returnModifiedArticleId() {
            var article = ArticleFixtures.article();
            var board = new Board(6L, "other board");

            given(loadArticlePort.findArticleById(any()))
                .willReturn(Optional.of(article));
            var modifiedArticle = new Article(article.getId(), board, "new subject", "new content", article.getUsername(), article.getCreatedAt());
            given(commandArticlePort.modifyArticle(any()))
                .willReturn(modifiedArticle);

            var result = sut.modifyArticle(request);

            then(result)
                .isEqualTo(modifiedArticle);
        }

        @Test
        @DisplayName("존재하지 않는 Article 이면 NoSuchElementException throw")
        void notExistArticle_throwNoSuchElementException() {
            given(loadArticlePort.findArticleById(any()))
                .willReturn(Optional.empty());

            thenThrownBy(() -> sut.modifyArticle(request))
                .isInstanceOf(NoSuchElementException.class);
        }

        @Test
        @DisplayName("user 가 다르면 AccessDeniedException throw")
        void otherUser_throwException() {
            var request = new ArticleDto.UpdateArticleRequest(6L, new BoardDto(6L, "board"), "new subject", "new content", "other user");

            var article = ArticleFixtures.article();
            given(loadArticlePort.findArticleById(any()))
                .willReturn(Optional.of(article));

            thenThrownBy(() -> sut.modifyArticle(request))
                .isInstanceOf(AccessDeniedException.class);
        }
    }

    @Test
    @DisplayName("Article 삭제")
    void deleteArticle() {
        willDoNothing()
            .given(commandArticlePort).deleteArticle(any());

        sut.deleteArticle(1L);

        verify(commandArticlePort).deleteArticle(1L);
    }
}
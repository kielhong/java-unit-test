package com.example.demo.article.application.service;

import static org.mockito.ArgumentMatchers.any;

import com.example.demo.article.application.port.out.LoadArticlePort;
import com.example.demo.article.domain.ArticleFixtures;
import com.example.demo.common.exception.ResourceNotFoundException;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

class GetArticleServiceTest {
    private GetArticleService sut;

    private LoadArticlePort loadArticlePort;

    @BeforeEach
    void setUp() {
        loadArticlePort = Mockito.mock(LoadArticlePort.class);

        sut = new GetArticleService(loadArticlePort);
    }

    @Test
    @DisplayName("articleId 로 조회시 Article 반환")
    void return_Article() {
        var article = ArticleFixtures.article();
        BDDMockito.given(loadArticlePort.findArticleById(any()))
            .willReturn(Optional.of(article));

        var result = sut.getArticleById(1L);

        BDDAssertions.then(result)
            .isNotNull()
            .hasFieldOrPropertyWithValue("id", article.getId())
            .hasFieldOrPropertyWithValue("board.id", article.getBoard().getId())
            .hasFieldOrPropertyWithValue("subject", article.getSubject())
            .hasFieldOrPropertyWithValue("content", article.getContent())
            .hasFieldOrPropertyWithValue("username", article.getUsername())
            .hasFieldOrPropertyWithValue("createdAt", article.getCreatedAt());
    }

    @Test
    @DisplayName("Article 존재하지 않을 경우 ResourceNotFoundException throw")
    void throw_ResourceNotFoundException() {
        BDDMockito.given(loadArticlePort.findArticleById(any()))
            .willReturn(Optional.empty());

        BDDAssertions.thenThrownBy(() -> sut.getArticleById(1L))
            .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("Board의 Article 목록 조회")
    void getArticlesByBoard_listArticles() {
        var article1 = ArticleFixtures.article(1L);
        var article2 = ArticleFixtures.article(2L);
        BDDMockito.given(loadArticlePort.findArticlesByBoardId(any()))
            .willReturn(List.of(article1, article2));

        var result = sut.getArticlesByBoard(5L);

        BDDAssertions.then(result)
            .hasSize(2)
            .extracting("board.id").containsOnly(5L);
    }
}

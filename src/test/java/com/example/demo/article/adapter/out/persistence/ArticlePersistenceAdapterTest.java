package com.example.demo.article.adapter.out.persistence;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

import com.example.demo.article.adapter.out.persistence.entity.ArticleJpaEntity;
import com.example.demo.article.adapter.out.persistence.entity.BoardJpaEntity;
import com.example.demo.article.adapter.out.persistence.repository.ArticleRepository;
import com.example.demo.article.domain.Article;
import com.example.demo.article.domain.Board;
import com.example.demo.article.out.persistence.ArticleJpaEntityFixtures;
import com.example.demo.article.out.persistence.BoardJpaEntityFixtures;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

class ArticlePersistenceAdapterTest {
    private ArticlePersistenceAdapter adapter;

    private ArticleRepository articleRepository;

    @BeforeEach
    void setUp() {
        articleRepository = Mockito.mock(ArticleRepository.class);

        adapter = new ArticlePersistenceAdapter(articleRepository);
    }

    @Test
    @DisplayName("articleId로 Article 한개 조회")
    void given_articleId_when_getById_then_return_Article() {
        var articleJpaEntity = ArticleJpaEntityFixtures.entity();
        given(articleRepository.findById(any()))
            .willReturn(Optional.of(articleJpaEntity));

        var result = adapter.findArticleById(1L);

        then(result)
            .isPresent()
            .hasValueSatisfying(article ->
                then(article)
                    .hasFieldOrPropertyWithValue("id", articleJpaEntity.getId())
                    .hasFieldOrPropertyWithValue("board.id", articleJpaEntity.getBoard().getId())
                    .hasFieldOrPropertyWithValue("subject", articleJpaEntity.getSubject())
                    .hasFieldOrPropertyWithValue("content", articleJpaEntity.getContent())
                    .hasFieldOrPropertyWithValue("createdAt", articleJpaEntity.getCreatedAt())
            );
    }

    @Test
    @DisplayName("boardId 에 속한 Article list 반환")
    void findArticlesByBoard_listArticle() {
        var articleJpaEntity1 = ArticleJpaEntityFixtures.entity(1L);
        var articleJpaEntity2 = ArticleJpaEntityFixtures.entity(2L);
        given(articleRepository.findByBoardId(any()))
            .willReturn(List.of(articleJpaEntity1, articleJpaEntity2));

        var result = adapter.findArticlesByBoardId(5L);

        then(result)
            .hasSize(2)
            .hasOnlyElementsOfType(Article.class);
    }

    @Nested
    @DisplayName("Article 생성")
    class CreateArticle {
        private final Article article = new Article(null, new Board(5L, "board"), "subject", "content", "user", LocalDateTime.now());

        @Test
        @DisplayName("응답값 검증")
        void createArticle_returnCreatedArticle() {
            var boardJpaEntity = BoardJpaEntityFixtures.board();
            var articleJpaEntity = new ArticleJpaEntity(boardJpaEntity, "subject", "content", "user",
                LocalDateTime.parse("2023-02-10T11:12:33"));
            ReflectionTestUtils.setField(articleJpaEntity, "id", 1L);
            given(articleRepository.save(any()))
                .willReturn(articleJpaEntity);

            var result = adapter.createArticle(article);

            then(result)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("board.id", 5L)
                .hasFieldOrPropertyWithValue("subject", "subject")
                .hasFieldOrPropertyWithValue("content", "content")
                .hasFieldOrPropertyWithValue("username", "user");
        }

        @Test
        @DisplayName("argumentCapture 검증")
        void createArticle_verifySaveArg() {
            ArgumentCaptor<ArticleJpaEntity> argumentCaptor = ArgumentCaptor.forClass(ArticleJpaEntity.class);
            var boardJpaEntity = BoardJpaEntityFixtures.board();
            var articleJpaEntity = new ArticleJpaEntity(boardJpaEntity, "subject", "content", "user",
                LocalDateTime.parse("2023-02-10T11:12:33"));
            ReflectionTestUtils.setField(articleJpaEntity, "id", 1L);
            given(articleRepository.save(any()))
                .willReturn(articleJpaEntity);

            adapter.createArticle(article);

            verify(articleRepository).save(argumentCaptor.capture());
            then(argumentCaptor.getValue())
                .hasFieldOrPropertyWithValue("id", null)
                .hasFieldOrPropertyWithValue("board.id", 5L)
                .hasFieldOrPropertyWithValue("subject", "subject")
                .hasFieldOrPropertyWithValue("content", "content")
                .hasFieldOrPropertyWithValue("username", "user");
        }
    }

    @Test
    @DisplayName("Article 변경")
    void modifyArticle() {
        ArgumentCaptor<ArticleJpaEntity> argumentCaptor = ArgumentCaptor.forClass(ArticleJpaEntity.class);

        final Article article = new Article(1L, new Board(6L, "new board"), "new subject", "new content", "new user", LocalDateTime.now());
        var boardJpaEntity = new BoardJpaEntity("new board");
        ReflectionTestUtils.setField(boardJpaEntity, "id", 6L);
        var articleJpaEntity = new ArticleJpaEntity(boardJpaEntity, "new subject", "new content", "new user",
            LocalDateTime.parse("2023-02-10T11:12:33"));
        ReflectionTestUtils.setField(articleJpaEntity, "id", 1L);
        given(articleRepository.save(any()))
            .willReturn(articleJpaEntity);

        adapter.modifyArticle(article);

        verify(articleRepository).save(argumentCaptor.capture());
        then(argumentCaptor.getValue())
            .hasFieldOrPropertyWithValue("id", 1L)
            .hasFieldOrPropertyWithValue("board.id", 6L)
            .hasFieldOrPropertyWithValue("subject", "new subject")
            .hasFieldOrPropertyWithValue("content", "new content")
            .hasFieldOrPropertyWithValue("username", "new user");
    }

    @Test
    @DisplayName("Article 삭제")
    void deleteArticle() {
        willDoNothing()
            .given(articleRepository).deleteById(any());

        adapter.deleteArticle(1L);

        verify(articleRepository).deleteById(1L);
    }
}
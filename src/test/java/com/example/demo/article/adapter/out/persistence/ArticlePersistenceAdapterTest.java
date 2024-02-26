package com.example.demo.article.adapter.out.persistence;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.example.demo.article.adapter.out.persistence.repository.ArticleRepository;
import com.example.demo.article.domain.Article;
import com.example.demo.article.out.persistence.ArticleJpaEntityFixtures;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
}
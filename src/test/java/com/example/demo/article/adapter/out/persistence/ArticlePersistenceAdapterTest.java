package com.example.demo.article.adapter.out.persistence;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.example.demo.article.adapter.out.persistence.entity.ArticleJpaEntity;
import com.example.demo.article.adapter.out.persistence.repository.ArticleRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ArticlePersistenceAdapterTest {
    private ArticlePersistenceAdapter adapter;

    private final ArticleRepository articleRepository = Mockito.mock(ArticleRepository.class);

    @BeforeEach
    void setUp() {
        adapter = new ArticlePersistenceAdapter(articleRepository);
    }

    @Test
    @DisplayName("articleId로 Article 한개 조회")
    void given_articleId_when_getById_then_return_Article() {
        Long articleId = 1L;
        var articleJpaEntity = new ArticleJpaEntity(10L, "subject", "content", "username", LocalDateTime.now());
        given(articleRepository.findById(any()))
            .willReturn(Optional.of(articleJpaEntity));

        var result = adapter.findArticleById(articleId);

        then(result)
            .isPresent()
            .hasValueSatisfying(article ->
                then(article)
                    .hasFieldOrPropertyWithValue("id", articleJpaEntity.getId())
                    .hasFieldOrPropertyWithValue("boardId", articleJpaEntity.getBoardId())
                    .hasFieldOrPropertyWithValue("subject", articleJpaEntity.getSubject())
                    .hasFieldOrPropertyWithValue("content", articleJpaEntity.getContent())
                    .hasFieldOrPropertyWithValue("createdAt", articleJpaEntity.getCreatedAt())
            );
    }
}
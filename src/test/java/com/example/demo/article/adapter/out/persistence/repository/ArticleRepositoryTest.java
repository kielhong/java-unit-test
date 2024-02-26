package com.example.demo.article.adapter.out.persistence.repository;

import static org.assertj.core.api.BDDAssertions.then;

import com.example.demo.article.adapter.out.persistence.entity.ArticleJpaEntity;
import com.example.demo.article.adapter.out.persistence.entity.BoardJpaEntity;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class ArticleRepositoryTest {
    @Autowired
    private ArticleRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    private BoardJpaEntity boardJpaEntity;

    @BeforeEach
    void setUp() {
        boardJpaEntity = entityManager.persist(new BoardJpaEntity("test"));

        entityManager.persist(new ArticleJpaEntity(boardJpaEntity, "subject1", "content1", "user", LocalDateTime.now()));
        entityManager.persist(new ArticleJpaEntity(boardJpaEntity, "subject2", "content2", "user", LocalDateTime.now()));
    }

    @Test
    void listAllArticles() {
        var result = repository.findByBoardId(boardJpaEntity.getId());

        then(result)
            .hasSize(2);
    }
}
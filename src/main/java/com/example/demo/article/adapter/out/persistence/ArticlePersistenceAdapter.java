package com.example.demo.article.adapter.out.persistence;

import com.example.demo.article.adapter.out.persistence.repository.ArticleRepository;
import com.example.demo.article.application.port.out.LoadArticlePort;
import com.example.demo.article.domain.Article;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class ArticlePersistenceAdapter implements LoadArticlePort {
    private final ArticleRepository articleRepository;

    public ArticlePersistenceAdapter(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Optional<Article> findArticleById(Long articleId) {
        return articleRepository.findById(articleId)
            .map(a -> new Article(a.getId(), a.getBoardId(), a.getSubject(), a.getContent(), a.getUsername(), a.getCreatedAt()));
    }

    @Override
    public List<Article> findArticlesByBoardId(Long boardId) {
        return Collections.emptyList();
    }
}

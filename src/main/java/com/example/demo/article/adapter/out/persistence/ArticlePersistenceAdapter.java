package com.example.demo.article.adapter.out.persistence;

import com.example.demo.article.adapter.out.persistence.repository.ArticleRepository;
import com.example.demo.article.application.port.in.dto.ArticleRequest;
import com.example.demo.article.application.port.out.CommandArticlePort;
import com.example.demo.article.application.port.out.LoadArticlePort;
import com.example.demo.article.domain.Article;
import com.example.demo.article.domain.Board;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class ArticlePersistenceAdapter implements LoadArticlePort, CommandArticlePort {
    private final ArticleRepository articleRepository;

    public ArticlePersistenceAdapter(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Optional<Article> findArticleById(Long articleId) {
        return articleRepository.findById(articleId)
            .map(article -> new Article(article.getId(), new Board(article.getBoard().getId(), article.getBoard().getName()), article.getSubject(), article.getContent(), article.getUsername(), article.getCreatedAt()));
    }

    @Override
    public List<Article> findArticlesByBoardId(Long boardId) {
        return Collections.emptyList();
    }

    @Override
    public Article createArticle(ArticleRequest request) {
        return null;
    }

    @Override
    public Article modifyArticle(ArticleRequest request) {
        return null;
    }
}

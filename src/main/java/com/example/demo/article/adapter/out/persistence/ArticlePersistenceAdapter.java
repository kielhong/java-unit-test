package com.example.demo.article.adapter.out.persistence;

import com.example.demo.article.adapter.out.persistence.entity.ArticleJpaEntity;
import com.example.demo.article.adapter.out.persistence.repository.ArticleRepository;
import com.example.demo.article.adapter.out.persistence.repository.BoardRepository;
import com.example.demo.article.application.port.in.dto.ArticleRequest;
import com.example.demo.article.application.port.out.CommandArticlePort;
import com.example.demo.article.application.port.out.LoadArticlePort;
import com.example.demo.article.domain.Article;
import com.example.demo.article.domain.Board;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class ArticlePersistenceAdapter implements LoadArticlePort, CommandArticlePort {
    private final ArticleRepository articleRepository;
    private final BoardRepository boardRepository;

    public ArticlePersistenceAdapter(ArticleRepository articleRepository, BoardRepository boardRepository) {
        this.articleRepository = articleRepository;
        this.boardRepository = boardRepository;
    }

    @Override
    public Optional<Article> findArticleById(Long articleId) {
        return articleRepository.findById(articleId)
            .map(article -> new Article(article.getId(), new Board(article.getBoard().getId(), article.getBoard().getName()), article.getSubject(), article.getContent(), article.getUsername(), article.getCreatedAt()));
    }

    @Override
    public List<Article> findArticlesByBoardId(Long boardId) {
        return articleRepository.findByBoardId(boardId).stream()
            .map(article -> new Article(article.getId(), new Board(article.getBoard().getId(), article.getBoard().getName()), article.getSubject(), article.getContent(), article.getUsername(), article.getCreatedAt()))
            .toList();
    }

    @Override
    public Article createArticle(Article article) {
        var boardJpaEntity = boardRepository.findById(article.getBoard().getId()).get();
        var articleJpaEntity = articleRepository.save(
            new ArticleJpaEntity(boardJpaEntity, article.getSubject(), article.getContent(), article.getUsername(), LocalDateTime.now()));

        return new Article(articleJpaEntity.getId(), new Board(articleJpaEntity.getBoard().getId(), articleJpaEntity.getBoard().getName()), articleJpaEntity.getSubject(), articleJpaEntity.getContent(), articleJpaEntity.getUsername(), articleJpaEntity.getCreatedAt());
    }

    @Override
    public Article modifyArticle(ArticleRequest request) {
        return null;
    }

    @Override
    public void deleteArticle(Long articleId) {

    }
}

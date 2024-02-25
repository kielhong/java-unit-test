package com.example.demo.article.application.port.out;

import com.example.demo.article.domain.Article;
import java.util.List;
import java.util.Optional;

public interface LoadArticlePort {
    Optional<Article> findArticleById(Long articleId);
    List<Article> findArticlesByBoardId(Long boardId);
}

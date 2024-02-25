package com.example.demo.article.application.port.in;

import com.example.demo.article.application.port.in.dto.ArticleResponse;
import com.example.demo.article.domain.Article;
import java.util.List;

public interface GetArticleUseCase {
    Article getById(Long articleId);

    List<Article> getArticlesByBoard(Long boardId);
}

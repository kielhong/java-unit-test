package com.example.demo.article.application.port.in;

import com.example.demo.article.application.port.in.dto.ArticleResponse;
import java.util.List;

public interface GetArticleUseCase {
    ArticleResponse getById(Long articleId);

    List<ArticleResponse> getArticlesByBoard(Long boardId);
}

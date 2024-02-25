package com.example.demo.article.adapter.in.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.article.application.port.in.GetArticleUseCase;
import com.example.demo.article.domain.Article;
import com.example.demo.article.domain.Board;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ArticleController.class)
class ArticleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetArticleUseCase getArticleUseCase;

    @Test
    @DisplayName("GET /articles/{articleId}")
    void getArticle() throws Exception {
        var board = new Board(5L, "board");
        var article = new Article(1L, board, "subject", "content", "username", LocalDateTime.now());
        given(getArticleUseCase.getArticleById(any()))
            .willReturn(article);

        Long articleId = 1L;
        mockMvc.perform(get("/articles/{articleId}", articleId))
            .andDo(print())
            .andExpectAll(
                status().isOk(),
                jsonPath("$.id").value(article.getId()),
                jsonPath("$.board.id").value(article.getBoard().getId()),
                jsonPath("$.subject").value(article.getSubject()),
                jsonPath("$.content").value(article.getContent()),
                jsonPath("$.username").value(article.getUsername()),
                jsonPath("$.createdAt").value(article.getCreatedAt().toString())
            );
    }
}
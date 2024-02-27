package com.example.demo.article.adapter.in.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.article.application.port.in.DeleteArticleUseCase;
import com.example.demo.article.application.port.in.GetArticleUseCase;
import com.example.demo.article.domain.ArticleFixtures;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ArticleController.class)
class ArticleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetArticleUseCase getArticleUseCase;
    @MockBean
    private DeleteArticleUseCase deleteArticleUseCase;

    @Nested
    @DisplayName("GET /articles/{articleId}")
    class GetArticle {
        @Test
        @DisplayName("Article이 있으면, 200 OK return response")
        void returnResponse() throws Exception {
            var article = ArticleFixtures.article();
            given(getArticleUseCase.getArticleById(any()))
                .willReturn(article);

            mockMvc.perform(get("/articles/{articleId}", 1L))
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

        @Test
        @DisplayName("articleId 에 해당하는 Article이 없으면 400 Not Found")
        void getArticle() throws Exception {
            given(getArticleUseCase.getArticleById(any()))
                .willThrow(new NoSuchElementException("article not exists"));

            mockMvc.perform(get("/articles/{articleId}", 1L))
                .andDo(print())
                .andExpectAll(
                    status().isNotFound()
                );
        }
    }

    @Test
    @DisplayName("GET /articles?boardId={boardId}")
    void listArticlesByBoard() throws Exception {
        given(getArticleUseCase.getArticlesByBoard(any()))
            .willReturn(List.of(ArticleFixtures.article(1L), ArticleFixtures.article(2L)));

        mockMvc.perform(get("/articles?boardId={boardId}", 5L))
            .andDo(print())
            .andExpectAll(
                status().isOk(),
                jsonPath("$.size()").value(2),
                jsonPath("$.[0].id").value(1L),
                jsonPath("$.[1].id").value(2L)
            );
    }

    @Test
    @DisplayName("DELETE /articles/{articleId}")
    void deleteArticle() throws Exception {
        mockMvc.perform(delete("/articles/{articleId}", 1L))
            .andDo(print())
            .andExpectAll(
                status().isOk()
            );
    }
}
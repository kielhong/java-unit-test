package com.example.demo.article.adapter.in.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.article.application.port.in.DeleteArticleUseCase;
import com.example.demo.article.application.port.in.GetArticleUseCase;
import com.example.demo.article.application.port.in.ModifyArticleUseCase;
import com.example.demo.article.application.port.in.PostArticleUseCase;
import com.example.demo.article.application.port.in.dto.ArticleRequest;
import com.example.demo.article.application.port.in.dto.BoardRequest;
import com.example.demo.article.domain.ArticleFixtures;
import com.example.demo.common.exception.AccessDeniedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ArticleController.class)
class ArticleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GetArticleUseCase getArticleUseCase;
    @MockBean
    private PostArticleUseCase postArticleUseCase;
    @MockBean
    private ModifyArticleUseCase modifyArticleUseCase;
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
        void notFound() throws Exception {
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

    @Nested
    @DisplayName("POST /articles")
    class PostArticle {
        @Test
        @DisplayName("생성된 articleId 반환")
        void returnArticleId() throws Exception {
            var createdArticle = ArticleFixtures.article();
            given(postArticleUseCase.postArticle(any()))
                .willReturn(createdArticle);

            var body = objectMapper.writeValueAsString(Map.of("board", Map.of("id", 5L, "name", "board"), "subject", "subject", "content", "content", "username", "user"));
            mockMvc
                .perform(
                    post("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andDo(print())
                .andExpectAll(
                    status().isOk(),
                    jsonPath("$.id").value(createdArticle.getId())
                );
        }

        @ParameterizedTest(name = "{0}")
        @CsvSource(
            value = {
                "subject is null,,content,user",
                "content is null,subject,,user",
                "username is null,subject,content,",
                "username is empty,subject,content,''"
            }
        )
        void invalidParam_BadRequest(String desc, String subject, String content, String username) throws Exception {
            var body = objectMapper.writeValueAsString(new ArticleRequest(null, new BoardRequest(5L, "board"), subject, content, username));
            mockMvc
                .perform(
                    post("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andDo(print())
                .andExpectAll(
                    status().isBadRequest()
                );
        }
    }

    @Nested
    @DisplayName("PUT /articles")
    class PutArticle {
        @Test
        @DisplayName("변경된 Article의 articleId 반환")
        void returnArticleId() throws Exception {
            var modifiedArticle = ArticleFixtures.article();
            given(modifyArticleUseCase.modifyArticle(any()))
                .willReturn(modifiedArticle);

            var body = objectMapper.writeValueAsString(Map.of("id", 1L, "board", Map.of("id", 5L, "name", "board"), "subject", "new subject", "content", "new content", "username", "user"));
            mockMvc
                .perform(
                    put("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andDo(print())
                .andExpectAll(
                    status().isOk(),
                    jsonPath("$.id").value(modifiedArticle.getId())
                );
        }

        @ParameterizedTest(name = "{0}")
        @CsvSource(
            value = {
                "subject is null,,content,user",
                "content is null,subject,,user"
            }
        )
        void invalidParam_BadRequest(String desc, String subject, String content, String username) throws Exception {
            var body = objectMapper.writeValueAsString(new ArticleRequest(null, new BoardRequest(5L, "board"), subject, content, username));
            mockMvc
                .perform(
                    put("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andDo(print())
                .andExpectAll(
                    status().isBadRequest()
                );
        }

        @Test
        void otherUser_Forbidden() throws Exception {
            given(modifyArticleUseCase.modifyArticle(any()))
                .willThrow(new AccessDeniedException("다른 작성자는 수정 불가능"));

            var body = objectMapper.writeValueAsString(Map.of("id", 1L, "board", Map.of("id", 5L, "name", "board"), "subject", "new subject", "content", "new content", "username", "otheruser"));
            mockMvc
                .perform(
                    put("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andDo(print())
                .andExpectAll(
                    status().isForbidden()
                );
        }
    }

    @Test
    @DisplayName("DELETE /articles/{articleId}")
    void deleteArticle() throws Exception {
        willDoNothing().given(deleteArticleUseCase).deleteArticle(any());

        mockMvc.perform(delete("/articles/{articleId}", 1L))
            .andDo(print())
            .andExpectAll(
                status().isOk()
            );

        verify(deleteArticleUseCase).deleteArticle(1L);
    }
}
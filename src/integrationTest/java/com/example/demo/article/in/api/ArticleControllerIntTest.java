package com.example.demo.article.in.api;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.article.adapter.out.persistence.repository.ArticleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc   // for MockMvc
@Transactional          // for data.sql
@Sql("/com/example/demo/article/in/api/ArticleControllerIntTest.sql")   // default value : same package, same class name
class ArticleControllerIntTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ArticleRepository articleRepository;

    @Nested
    @DisplayName("GET /articles/{articleId}")
    class GetArticle {
        @Test
        @DisplayName("Article 반환하고 200 OK")
        void returnArticle() throws Exception {
            mockMvc
                .perform(get("/articles/{articleId}", 1L))
                .andExpectAll(
                    status().isOk(),
                    jsonPath("$.id").value(1L)
                );
        }

        @Test
        @DisplayName("Article 없으면 404 NotFound")
        void badRequest() throws Exception {
            mockMvc
                .perform(get("/articles/{articleId}", 10L))
                .andExpect(
                    status().isNotFound()
                );
        }
    }

    @Nested
    @DisplayName("POST /articles")
    class PostArticle {
        @Test
        @DisplayName("Article 생성 200 OK, DB에서 생성됨")
        void createArticle() throws Exception {
            var count = articleRepository.count();

            var body = objectMapper.writeValueAsString(Map.of("boardId", 5, "subject", "subject", "content", "content", "username", "user"));
            mockMvc
                .perform(
                    post("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andExpectAll(
                    status().isOk(),
                    content().contentType(MediaType.APPLICATION_JSON),
                    jsonPath("$.id").exists()
                );

            then(articleRepository.count())
                .isEqualTo(count + 1);
        }
    }

    @Nested
    @DisplayName("DELETE /articles/{articleId}")
    class DeleteArticle {
        @Test
        @DisplayName("Article 삭제 200 OK, DB에서 제거됨")
        void createArticle() throws Exception {
            mockMvc
                .perform(
                    delete("/articles/{articleId}", 1L)
                )
                .andExpectAll(
                    status().isOk()
                );

            then(articleRepository.findById(1L))
                .isNotPresent();
        }
    }
}

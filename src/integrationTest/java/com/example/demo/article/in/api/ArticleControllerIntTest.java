package com.example.demo.article.in.api;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
@Transactional          // for DB data
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
                .andDo(print())
                .andExpectAll(
                    status().isOk(),
                    jsonPath("$.id").value(1L),
                    jsonPath("$.subject").value("subject1")
                );
        }

        @Test
        @DisplayName("Article 없으면 404 NotFound")
        void notFound() throws Exception {
            mockMvc
                .perform(get("/articles/{articleId}", 10L))
                .andDo(print())
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
                .andDo(print())
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
            var articleId = 1L;
            var count = articleRepository.count();
            mockMvc
                .perform(
                    delete("/articles/{articleId}", articleId)
                )
                .andDo(print())
                .andExpectAll(
                    status().isOk()
                );

            then(articleRepository.findById(articleId))
                .isNotPresent();
            then(articleRepository.count())
                .isEqualTo(count - 1);
        }
    }
}

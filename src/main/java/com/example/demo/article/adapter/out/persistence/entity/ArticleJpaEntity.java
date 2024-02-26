package com.example.demo.article.adapter.out.persistence.entity;

import com.example.demo.article.domain.Article;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "article")
@NoArgsConstructor
@Getter
public class ArticleJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private BoardJpaEntity board;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public ArticleJpaEntity(BoardJpaEntity board, String subject, String content, String username, LocalDateTime createdAt) {
        this.board = board;
        this.subject = subject;
        this.content = content;
        this.username = username;
        this.createdAt = createdAt;
    }

    private ArticleJpaEntity(Long id, BoardJpaEntity board, String subject, String content, String username, LocalDateTime createdAt) {
        this.id = id;
        this.board = board;
        this.subject = subject;
        this.content = content;
        this.username = username;
        this.createdAt = createdAt;
    }

    public Article toDomain() {
        return new Article(
            this.id,
            this.board.toDomain(),
            this.subject,
            this.content,
            this.username,
            this.createdAt
        );
    }

    public static ArticleJpaEntity fromDomain(Article article) {
        return new ArticleJpaEntity(
            article.getId(),
            BoardJpaEntity.fromDomain(article.getBoard()),
            article.getSubject(),
            article.getContent(),
            article.getUsername(),
            article.getCreatedAt()
        );
    }
}

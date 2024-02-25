package com.example.demo.article.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "board")
@NoArgsConstructor
@Getter
public class BoardJpaEntity {
    @Id
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    public BoardJpaEntity(String name) {
        this.name = name;
    }
}

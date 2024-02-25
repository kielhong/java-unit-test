package com.example.demo.article.application.port.in.dto;

public record ArticleRequest(
    Long boardId,
    String subject,
    String content,
    String username
) { }
package com.example.demo.common.web.dto;

import java.time.LocalDateTime;

public record ErrorMessage(
    Integer statusCode,
    String message,
    LocalDateTime timestamp
) {}

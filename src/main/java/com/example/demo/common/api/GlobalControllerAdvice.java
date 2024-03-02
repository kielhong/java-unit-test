package com.example.demo.common.api;

import com.example.demo.common.api.dto.ErrorMessage;
import com.example.demo.common.exception.AccessDeniedException;
import com.example.demo.common.exception.ResourceNotFoundException;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleNoSuchElementException(ResourceNotFoundException ex)  {
        return new ErrorMessage(HttpStatus.NOT_FOUND.value(), ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage handleAccessDeniedException(AccessDeniedException ex) {
        return new ErrorMessage(HttpStatus.FORBIDDEN.value(), ex.getMessage(), LocalDateTime.now());
    }
}

package com.sparta.impostor.commerce.backend.common.exception.dto;

import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public record ErrorResponse(
        int statusCode,
        LocalDateTime timestamp,
        String message
) {
    public static ErrorResponse of(int value, LocalDateTime now, String message) {
        return new ErrorResponse(value, now, message);
    }
}

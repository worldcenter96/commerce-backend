package com.sparta.impostor.commerce.backend.common.exception;

import com.sparta.impostor.commerce.backend.common.exception.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> ResourceNotFoundException(ResourceNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        ErrorResponse response = ErrorResponse.of(status.value(), LocalDateTime.now(), ex.getMessage());

        return ResponseEntity
                .status(status)
                .body(response);

    }

    @ExceptionHandler(PermissionDeniedException.class)
    public ResponseEntity<ErrorResponse> ResourceNotFoundException(PermissionDeniedException ex) {
        HttpStatus status = HttpStatus.FORBIDDEN;

        ErrorResponse response = ErrorResponse.of(status.value(), LocalDateTime.now(), ex.getMessage());

        return ResponseEntity
                .status(status)
                .body(response);

    }

}

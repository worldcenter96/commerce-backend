package com.sparta.b2c.exception;

import com.sparta.impostor.commerce.backend.common.exception.PermissionDeniedException;

public class AccessDeniedException extends PermissionDeniedException {

    public AccessDeniedException(String message) {
        super(message);
    }
}

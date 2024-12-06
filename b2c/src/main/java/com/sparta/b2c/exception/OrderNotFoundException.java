package com.sparta.b2c.exception;

import com.sparta.impostor.commerce.backend.common.exception.ResourceNotFoundException;

public class OrderNotFoundException extends ResourceNotFoundException {

    public OrderNotFoundException(String message) {
        super(message);
    }
}

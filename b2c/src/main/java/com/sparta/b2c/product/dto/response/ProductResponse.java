package com.sparta.b2c.product.dto.response;

import com.sparta.impostor.commerce.backend.domain.product.enums.Category;
import com.sparta.impostor.commerce.backend.domain.product.enums.ProductStatus;

import java.time.LocalDateTime;

public record ProductResponse (
        Long id,
        String name,
        String description,
        int price,
        ProductStatus status,
        Category category,
        Category.SubCategory subCategory,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {}

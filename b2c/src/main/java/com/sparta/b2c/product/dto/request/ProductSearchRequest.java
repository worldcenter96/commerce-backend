package com.sparta.b2c.product.dto.request;

import com.sparta.impostor.commerce.backend.domain.product.enums.Category;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ProductSearchRequest(

        @Min(value = 1, message = "page는 1보다 작을 수 없습니다.")
        int page,

        @Max(value = 50, message = "size를 50을 넘길 수 없습니다.")
        int size,

        @NotBlank(message = "키워드는 필수 입력입니다.")
        String keyword,

        @Pattern(regexp = "ASC|DESC", message = "orderBy에는 ASC 또는 DESC만 입력할 수 있습니다.")
        String orderBy,

        @Pattern(regexp = "name|price|createdAt", message = "sortBy에는 'name', 'price' 'createdAt'만 입력할 수 있습니다.")
        String sortBy,

        Category category,

        Category.SubCategory subCategory
) {
}

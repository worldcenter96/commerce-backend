package com.sparta.b2c.product.dto.request;

import com.sparta.impostor.commerce.backend.domain.product.enums.Category;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public record RelatedProductRetrieveRequest (
        @Min(value = 1, message = "page는 1보다 작을 수 없습니다.")
        int page,

        @Max(value = 50, message = "size를 50을 넘길 수 없습니다.")
        int size,

        Category.SubCategory subCategory
) {
}

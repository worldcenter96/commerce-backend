package com.sparta.b2c.order.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderRequest(

    @NotNull
    Long productId,

    @Positive
    @Min(value = 1, message = "수량은 1보다 작을 수 없습니다.")
    int quantity
) {
}

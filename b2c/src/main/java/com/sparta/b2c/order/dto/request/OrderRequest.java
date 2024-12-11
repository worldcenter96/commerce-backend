package com.sparta.b2c.order.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderRequest {

    @NotNull
    private Long productId;

    @Positive
    @Min(value = 1, message = "수량은 1보다 작을 수 없습니다.")
    private int quantity;
}

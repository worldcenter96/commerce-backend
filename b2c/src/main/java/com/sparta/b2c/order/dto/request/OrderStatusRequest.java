package com.sparta.b2c.order.dto.request;

import jakarta.validation.constraints.NotNull;

public record OrderStatusRequest(
        @NotNull
        String orderStatus  // REFUND_REQUEST로 변환 요청
) {
}

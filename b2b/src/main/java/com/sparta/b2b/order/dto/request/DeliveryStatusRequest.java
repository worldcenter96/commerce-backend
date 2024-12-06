package com.sparta.b2b.order.dto.request;

import jakarta.validation.constraints.Size;

public record DeliveryStatusRequest(
        @Size(min = 12, max = 12, message = "숫자는 12자리여야 합니다.")
        String trackingNumber
) {
}

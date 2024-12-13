package com.sparta.b2b.product.dto.request;

import com.sparta.impostor.commerce.backend.domain.product.entity.Product;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductUpdateRequest(
	@NotNull
	@PositiveOrZero(message = "재고 수량은 0 이상이어야 합니다.")
	int stockQuantity
) {

}

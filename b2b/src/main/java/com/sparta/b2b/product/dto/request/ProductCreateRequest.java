package com.sparta.b2b.product.dto.request;

import com.sparta.impostor.commerce.backend.domain.product.entity.Product;
import com.sparta.impostor.commerce.backend.domain.product.enums.Category;
import com.sparta.impostor.commerce.backend.domain.product.enums.ProductStatus;

public record ProductCreateRequest(
	String name,
	String description,
	int stockQuantity,
	int price,
	Category category,
	Category.SubCategory subCategory
) {
	public Product from() {
		return new Product(
			this.name,
			this.description,
			this.stockQuantity,
			this.price,
			ProductStatus.PENDING,
			this.category,
			this.subCategory
		);
	}
}
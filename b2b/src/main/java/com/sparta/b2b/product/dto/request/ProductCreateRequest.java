package com.sparta.b2b.product.dto.request;

import com.sparta.impostor.commerce.backend.domain.product.enums.Category;
import com.sparta.impostor.commerce.backend.domain.product.entity.Product;
import com.sparta.impostor.commerce.backend.domain.product.enums.ProductStatus;

public record ProductCreateRequest(
	String name,
	String description,
	int stockQuantity,
	int price,
	Category category,
	Category.SubCategory subCategory
) {

	public Product toEntity() {
		return Product.builder()
			.name(name)
			.description(description)
			.stockQuantity(stockQuantity)
			.price(price)
			.category(category)
			.status(ProductStatus.PENDING)
			.subCategory(subCategory)
			.build();
	}

}
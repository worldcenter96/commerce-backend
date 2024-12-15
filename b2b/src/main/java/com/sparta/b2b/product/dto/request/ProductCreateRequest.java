package com.sparta.b2b.product.dto.request;

import com.sparta.b2b.fileUpload.dto.ImageInfo;
import com.sparta.impostor.commerce.backend.domain.b2bMember.entity.B2BMember;
import com.sparta.impostor.commerce.backend.domain.product.entity.Product;
import com.sparta.impostor.commerce.backend.domain.product.enums.Category;
import com.sparta.impostor.commerce.backend.domain.product.enums.ProductStatus;
import jakarta.validation.constraints.*;

import java.util.List;

public record ProductCreateRequest(
	@NotNull
	@NotBlank
	@Size(min = 2, max = 50, message = "이름은 2자 이상, 50자 이하로 입력해야 합니다.")
	String name,

	@NotNull
	@NotBlank
	@Size(max = 250, message = "설명은 250자 이하로 입력해야 합니다.")
	String description,

	@NotNull
	@PositiveOrZero(message = "재고 수량은 0 이상이어야 합니다.")
	int stockQuantity,

	@NotNull
	@Positive(message = "가격은 0보다 커야 합니다.")
	int price,

	@NotNull
	Category category,

	@NotNull
	Category.SubCategory subCategory
) {

	public Product toProductEntity(B2BMember member) {
		return Product.createProduct(
			this.name,
			this.description,
			this.stockQuantity,
			this.price,
			ProductStatus.PENDING,
			this.category,
			this.subCategory,
			member
		);
	}

}
package com.sparta.b2b.product.dto.response;

import com.sparta.b2b.fileUpload.dto.ImageInfo;
import com.sparta.impostor.commerce.backend.domain.image.entity.Image;
import com.sparta.impostor.commerce.backend.domain.product.entity.Product;
import com.sparta.impostor.commerce.backend.domain.product.enums.Category;
import com.sparta.impostor.commerce.backend.domain.product.enums.ProductStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record ProductCreateResponse(
	Long id,
	String name,
	String description,
	int stockQuantity,
	int price,
	ProductStatus status,
	Category category,
	Category.SubCategory subCategory,
	LocalDateTime createdAt,
	LocalDateTime modifiedAt,
	List<ImageInfo> imageInfos
) {

	public static ProductCreateResponse from(Product save, List<Image> savedImageList) {

		List<ImageInfo> imageInfos = new ArrayList<>();
		for (Image image : savedImageList) {
			imageInfos.add(ImageInfo.builder()
				.url(image.getImgUrl())
				.build());
		}

		return new ProductCreateResponse(
			save.getId(),
			save.getName(),
			save.getDescription(),
			save.getStockQuantity(),
			save.getPrice(),
			save.getStatus(),
			save.getCategory(),
			save.getSubCategory(),
			save.getCreatedAt(),
			save.getModifiedAt(),
			imageInfos
		);
	}

}

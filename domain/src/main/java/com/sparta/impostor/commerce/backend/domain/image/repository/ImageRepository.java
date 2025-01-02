package com.sparta.impostor.commerce.backend.domain.image.repository;


import com.sparta.impostor.commerce.backend.domain.image.entity.Image;
import com.sparta.impostor.commerce.backend.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

	List<Image> findAllByProduct(Product product);
	List<Image> findByProductId(Long productId);

	// 매핑이 안된 이미지들 모두 조회
	List<Image> findAllByProductIsNull();
}

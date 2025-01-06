package com.sparta.impostor.commerce.backend.domain.product.repository;

import com.sparta.impostor.commerce.backend.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository <Product, Long>, ProductRepositoryQuery {
	Page<Product> findAllByMemberId(Long memberId, Pageable pageable);

	Optional<Product> findByIdAndMemberId(Long productId, Long memberId);
}

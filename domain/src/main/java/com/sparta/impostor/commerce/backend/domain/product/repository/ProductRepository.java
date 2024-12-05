package com.sparta.impostor.commerce.backend.domain.product.repository;

import com.sparta.impostor.commerce.backend.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository <Product, Long> {
}

package com.sparta.impostor.commerce.backend.domain.product.repository;

import com.sparta.impostor.commerce.backend.domain.product.entity.Product;
import com.sparta.impostor.commerce.backend.domain.product.enums.Category;
import com.sparta.impostor.commerce.backend.domain.product.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductRepositoryQuery {

    Page<Product> searchProductsWithFilters(String keyword, ProductStatus productStatus, Category category, Category.SubCategory subCategory, Pageable pageable);
    Page<Product> retrieveRelatedProducts(ProductStatus productStatus, Category.SubCategory subCategory, Pageable pageable);
}

package com.sparta.b2c.product.service;

import com.sparta.b2c.product.dto.request.ProductSearchRequest;
import com.sparta.b2c.product.dto.response.PageProductResponse;
import com.sparta.b2c.product.dto.response.ProductResponse;
import com.sparta.impostor.commerce.backend.common.exception.ForbiddenAccessException;
import com.sparta.impostor.commerce.backend.domain.product.entity.Product;
import com.sparta.impostor.commerce.backend.domain.product.enums.ProductStatus;
import com.sparta.impostor.commerce.backend.domain.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse retrieveProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("상품을 찾지 못하였습니다."));
        if (List.of(
                ProductStatus.OFF_SALE,
                ProductStatus.PENDING
        ).contains(product.getStatus())) {
            throw new ForbiddenAccessException("해당 상품은 조회할 수 없습니다.");
        }

        return ProductResponse.from(product);
    }

    public PageProductResponse searchProducts(ProductSearchRequest reqDto) {
        Sort.Direction direction = "ASC".equalsIgnoreCase(reqDto.orderBy()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, reqDto.sortBy());
        Pageable pageable = PageRequest.of(reqDto.page() - 1, reqDto.size(), sort);
        Page<Product> products = productRepository.searchProductsWithFilters(reqDto.keyword(), ProductStatus.ON_SALE, reqDto.category(), reqDto.subCategory(), pageable);

        return PageProductResponse.from(products);
    }
}

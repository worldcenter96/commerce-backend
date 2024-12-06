package com.sparta.b2c.product.service;

import com.sparta.b2c.product.dto.response.ProductResponse;
import com.sparta.impostor.commerce.backend.domain.product.entity.Product;
import com.sparta.impostor.commerce.backend.domain.product.enums.ProductStatus;
import com.sparta.impostor.commerce.backend.domain.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
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
            throw new AccessDeniedException("해당 상품은 조회할 수 없습니다.");
        }

        return ProductResponse.from(product);
    }
}

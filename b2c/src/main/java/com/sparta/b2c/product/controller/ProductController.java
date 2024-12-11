package com.sparta.b2c.product.controller;

import com.sparta.b2c.product.dto.request.ProductSearchRequest;
import com.sparta.b2c.product.dto.response.PageProductResponse;
import com.sparta.b2c.product.dto.response.ProductResponse;
import com.sparta.b2c.product.service.ProductService;
import com.sparta.impostor.commerce.backend.domain.product.enums.Category;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;
    private final Validator validator;

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponse> retrieveProduct(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.retrieveProduct(id));
    }

    @GetMapping("/products")
    public ResponseEntity<PageProductResponse> searchProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "asc") String orderBy,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "DEFAULT") Category category,
            @RequestParam(defaultValue = "DEFAULT") Category.SubCategory subCategory
    ) {

        ProductSearchRequest request = new ProductSearchRequest(page, size, keyword, orderBy, sortBy, category, subCategory);

        // Validation 추가 수동 검증
        Set<ConstraintViolation<ProductSearchRequest>> violations = validator.validate(request);
        violations.stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()  // 첫 번째 violation만 처리
                .ifPresent(message -> {
                    throw new IllegalArgumentException(message);
                });

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.searchProducts(request));
    }
}
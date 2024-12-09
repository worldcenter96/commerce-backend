package com.sparta.b2b.product.controller;

import com.sparta.b2b.product.dto.request.ProductCreateRequest;
import com.sparta.b2b.product.dto.response.ProductCreateResponse;
import com.sparta.b2b.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

	private final ProductService productService;

	@PostMapping()
	public ResponseEntity<ProductCreateResponse> createProduct(
		@RequestBody @Valid ProductCreateRequest request
	) {
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(productService.createProduct(1L, request));// test용 member, 로그인기능 완료시 수정
	}

}

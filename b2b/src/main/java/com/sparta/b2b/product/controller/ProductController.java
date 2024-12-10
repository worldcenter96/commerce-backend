package com.sparta.b2b.product.controller;

import com.sparta.b2b.product.dto.request.ProductCreateRequest;
import com.sparta.b2b.product.dto.response.ProductCreateResponse;
import com.sparta.b2b.product.dto.response.ProductSearchResponse;
import com.sparta.b2b.product.service.ProductService;
import com.sparta.impostor.commerce.backend.domain.product.entity.Product;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping("/{id}")
	public ResponseEntity<ProductSearchResponse> searchProduct(
		@PathVariable Long id
	) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(productService.searchProduct(1L, id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(
		@PathVariable Long id
	) {
		productService.deleteProduct(1L, id);
		return ResponseEntity
			.status(HttpStatus.NO_CONTENT).build();
	}
}

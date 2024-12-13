package com.sparta.admin.product.service;

import com.sparta.admin.product.dto.request.ProductApprovalRequest;
import com.sparta.admin.product.dto.response.ProductApprovalResponse;
import com.sparta.impostor.commerce.backend.domain.product.entity.Product;
import com.sparta.impostor.commerce.backend.domain.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductApprovalService {

  private final ProductRepository productRepository;

  public ProductApprovalService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  // 상품 등록 승인 처리
  public ProductApprovalResponse approveProduct(Long productId, ProductApprovalRequest request) {

    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

    Product updatedProduct = product.changeStatus(request.status());

    return ProductApprovalResponse.from(updatedProduct);
  }
}
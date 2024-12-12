package com.sparta.admin.product.controller;

import com.sparta.admin.product.dto.request.ProductApprovalRequest;
import com.sparta.admin.product.dto.response.ProductApprovalResponse;
import com.sparta.admin.product.service.ProductApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/products/approval")
public class ProductApprovalController {

  private final ProductApprovalService productApprovalService;


  // 상품 등록 승인 및 거절
  @PatchMapping("/{productId}")
  public ResponseEntity<ProductApprovalResponse> approveProduct(@PathVariable Long productId,
      @RequestBody ProductApprovalRequest request) {

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(productApprovalService.approveProduct(productId, request));
  }
}


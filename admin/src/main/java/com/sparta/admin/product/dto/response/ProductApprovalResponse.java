package com.sparta.admin.product.dto.response;

import com.sparta.impostor.commerce.backend.domain.product.entity.Product;
import com.sparta.impostor.commerce.backend.domain.product.enums.ProductStatus;
import java.time.LocalDateTime;

public record ProductApprovalResponse(
    ProductStatus status,
    LocalDateTime modifiedAt
) {

  public static ProductApprovalResponse from(Product product) {
    return new ProductApprovalResponse(
        product.getStatus(),
        product.getModifiedAt()
    );
  }
}

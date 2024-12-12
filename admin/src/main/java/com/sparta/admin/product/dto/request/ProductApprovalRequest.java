package com.sparta.admin.product.dto.request;

import com.sparta.impostor.commerce.backend.domain.product.enums.ProductStatus;

public record ProductApprovalRequest(
    ProductStatus status
) {

}

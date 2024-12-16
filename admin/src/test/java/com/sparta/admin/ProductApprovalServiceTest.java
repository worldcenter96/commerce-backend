package com.sparta.admin;

import com.sparta.admin.product.dto.request.ProductApprovalRequest;
import com.sparta.admin.product.dto.response.ProductApprovalResponse;
import com.sparta.admin.product.service.ProductApprovalService;
import com.sparta.impostor.commerce.backend.domain.product.entity.Product;
import com.sparta.impostor.commerce.backend.domain.product.enums.Category;
import com.sparta.impostor.commerce.backend.domain.product.enums.ProductStatus;
import com.sparta.impostor.commerce.backend.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductApprovalServiceTest {

    @Mock
    private ProductRepository productRepository;

    private ProductApprovalService productApprovalService;

    private Product product;

    @BeforeEach
    public void setUp() {
        productApprovalService = new ProductApprovalService(productRepository);

        // 상품 초기화 (생성자를 통해 초기화)
        product = Product.createProduct(
                "Product A",
                "Description for Product A",
                100,
                2000,
                ProductStatus.PENDING,
                Category.DEFAULT,
                Category.SubCategory.DEFAULT,
                null
        );
    }

    // 1. 상품 등록 승인 테스트
    @Test
    @DisplayName("상품등록 승인 성공 테스트")
    void testApproveProduct_Success() {

        // Given: 승인 요청
        ProductApprovalRequest request = new ProductApprovalRequest(ProductStatus.ON_SALE);

        // ProductRepository 의 findById 메서드를 mock 하여 특정 결과 반환 설정
        Mockito.when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));

        // When: 상품 승인 (ON_SALE 로 상태를 변경)
        ProductApprovalResponse response = productApprovalService.approveProduct(1L, request);

        // Then: 상품 상태가 ON_SALE 로 변경되고 응답이 올바르게 반환되는지 확인
        assertEquals(ProductStatus.ON_SALE, response.status());
        verify(productRepository, times(1)).findById(1L);
    }

    // 2. 상품 등록 거절 테스트
    @Test
    @DisplayName("상품 등록 거절 테스트")
    void testRejectProduct_Success() {

        // Given: 거절 요청
        ProductApprovalRequest request = new ProductApprovalRequest(ProductStatus.OFF_SALE);

        Mockito.when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));

        // When: 상품 거절 (OFF_SALE 로 상태를 변경)
        ProductApprovalResponse response = productApprovalService.approveProduct(1L, request);

        // Then: 상품 상태가 OFF_SALE 로 변경되고 응답이 올바르게 반환되는지 확인
        assertEquals(ProductStatus.OFF_SALE, response.status());
        verify(productRepository, times(1)).findById(1L);
    }

    // 3. 상품을 찾을 수 없는 경우 예외 처리 테스트
    @Test
    @DisplayName("상품 조회 실패 테스트")
    void testProductNotFound() {

        // Given: 상품이 존재하지 않음
        Mockito.when(productRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // When & Then: 예외 발생
        assertThrows(IllegalArgumentException.class, () -> {
            productApprovalService.approveProduct(1L, new ProductApprovalRequest(ProductStatus.ON_SALE));
        });
    }
}

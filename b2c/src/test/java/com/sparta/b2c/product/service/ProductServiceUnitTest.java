package com.sparta.b2c.product.service;

import com.sparta.b2c.product.dto.response.ProductResponse;
import com.sparta.impostor.commerce.backend.domain.product.entity.Product;
import com.sparta.impostor.commerce.backend.domain.product.enums.Category;
import com.sparta.impostor.commerce.backend.domain.product.enums.ProductStatus;
import com.sparta.impostor.commerce.backend.domain.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceUnitTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("E2C_Member 상품 단건 조회 성공 시")
    public void retrieveProductSuccessTest1 () {
        // given
        Long id = 1L;
        Product product = Product.builder()
                .id(id)
                .name("Sample001")
                .description("상품설명")
                .stockQuantity(100)
                .price(10000)
                .status(ProductStatus.ON_SALE)
                .category(Category.TOP)
                .subCategory(Category.SubCategory.T_SHIRT)
                .build();
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        // when
        ProductResponse productResp = productService.retrieveProduct(id);

        // then
        assertThat(productResp.id()).isEqualTo(1L);
        assertThat(productResp.name()).isEqualTo("Sample001");


    }

    @Test
    @DisplayName("없는 상품 단건 조회 실패 테스트 - EntityNotFoundException 예외 발생")
    public void retrieveFailTest1 () {
        // given
        Long invalidId = 999L;
        when(productRepository.findById(invalidId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> productService.retrieveProduct(invalidId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("상품을 찾지 못하였습니다.");

        verify(productRepository, times(1)).findById(invalidId);
    }

    @ParameterizedTest
    @EnumSource(value = ProductStatus.class, names = {"OFF_SALE", "PENDING"})
    @DisplayName("단건 조회할 수 없는 상품 상태 실패 테스트 - AccessDeniedException 예외 발생")
    public void retrieveFailTest2 (ProductStatus status) {
        // given
        Long id = 1L;
        Product product = Product.builder()
                .id(id)
                .name("Sample001")
                .description("상품설명")
                .stockQuantity(100)
                .price(10000)
                .status(status)
                .category(Category.TOP)
                .subCategory(Category.SubCategory.T_SHIRT)
                .build();
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        // when & then
        assertThatThrownBy(() -> productService.retrieveProduct(id))
                .isInstanceOf(AccessDeniedException.class)
                .hasMessage("해당 상품은 조회할 수 없습니다.");
    }
}
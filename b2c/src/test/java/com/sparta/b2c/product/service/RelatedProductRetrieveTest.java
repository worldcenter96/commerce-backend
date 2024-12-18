package com.sparta.b2c.product.service;

import com.sparta.b2c.product.dto.request.RelatedProductRetrieveRequest;
import com.sparta.b2c.product.dto.response.PageProductResponse;
import com.sparta.impostor.commerce.backend.common.config.JPAConfiguration;
import com.sparta.impostor.commerce.backend.common.config.PasswordEncoderConfig;
import com.sparta.impostor.commerce.backend.domain.b2bMember.entity.B2BMember;
import com.sparta.impostor.commerce.backend.domain.b2bMember.repository.B2BMemberRepository;
import com.sparta.impostor.commerce.backend.domain.b2cMember.entity.B2CMember;
import com.sparta.impostor.commerce.backend.domain.b2cMember.repository.B2CMemberRepository;
import com.sparta.impostor.commerce.backend.domain.order.entity.Orders;
import com.sparta.impostor.commerce.backend.domain.order.enums.DeliveryStatus;
import com.sparta.impostor.commerce.backend.domain.order.enums.OrderStatus;
import com.sparta.impostor.commerce.backend.domain.order.repository.OrderRepository;
import com.sparta.impostor.commerce.backend.domain.product.entity.Product;
import com.sparta.impostor.commerce.backend.domain.product.enums.Category;
import com.sparta.impostor.commerce.backend.domain.product.enums.ProductStatus;
import com.sparta.impostor.commerce.backend.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Import(value = {JPAConfiguration.class, ProductService.class, PasswordEncoderConfig.class})
public class RelatedProductRetrieveTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private B2BMemberRepository b2BMemberRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private B2CMemberRepository b2CMemberRepository;

    @Test
    @DisplayName("관련 상품 추천 조회 정렬 테스트 - 주문량 내림차순, 가격 오름차순")
    public void relatedProductSuccessTest1() {
        // given
        RelatedProductRetrieveRequest reqDto = new RelatedProductRetrieveRequest(
                1, 10, Category.SubCategory.T_SHIRT
        );
        // 판매자 생성
        B2BMember seller = b2BMemberRepository.save(B2BMember.createMember(
                "seller@email.com",
                passwordEncoder.encode("123456789"),
                "판매자"));

        // 상품 3개 생성
        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            productList.add(productRepository.save(Product.createProduct(
                    "상품" + i,
                    "상품설명",
                    100,
                    10000 * (i + 1),
                    ProductStatus.ON_SALE,
                    Category.TOP,
                    Category.SubCategory.T_SHIRT,
                    seller
            )));
        }

        // 구매자 생성
        B2CMember b2CMember = b2CMemberRepository.save(B2CMember.createMember(
                "b2c@email.com",
                passwordEncoder.encode("123456789"),
                "구매자"));

        // 주문 생성
        orderRepository.save(Orders.create(
                "상품3",
                1,
                30000L,
                OrderStatus.CONFIRMED,
                DeliveryStatus.DELIVERED,
                "",
                productList.get(2),
                b2CMember,
                seller.getId()
        ));

        // when
        PageProductResponse resDto = productService.retrieveRelatedProducts(reqDto);

        // then
        // 정렬 확인
        assertThat(resDto.contents()).hasSize(3);
        assertThat(resDto.contents().get(0).id()).isEqualTo(productList.get(2).getId());
        assertThat(resDto.contents().get(1).id()).isEqualTo(productList.get(0).getId());
        assertThat(resDto.contents().get(2).id()).isEqualTo(productList.get(1).getId());
    }
}

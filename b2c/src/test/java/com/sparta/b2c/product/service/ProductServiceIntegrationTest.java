package com.sparta.b2c.product.service;

import com.sparta.b2c.product.dto.request.ProductSearchRequest;
import com.sparta.b2c.product.dto.response.PageProductResponse;
import com.sparta.b2c.product.dto.response.ProductResponse;
import com.sparta.impostor.commerce.backend.common.config.JPAConfiguration;
import com.sparta.impostor.commerce.backend.common.config.PasswordEncoderConfig;
import com.sparta.impostor.commerce.backend.domain.b2bMember.entity.B2BMember;
import com.sparta.impostor.commerce.backend.domain.b2bMember.repository.B2BMemberRepository;
import com.sparta.impostor.commerce.backend.domain.product.entity.Product;
import com.sparta.impostor.commerce.backend.domain.product.enums.Category;
import com.sparta.impostor.commerce.backend.domain.product.enums.ProductStatus;
import com.sparta.impostor.commerce.backend.domain.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(value = {JPAConfiguration.class, ProductService.class, PasswordEncoderConfig.class})
public class ProductServiceIntegrationTest {

   @Autowired
   private ProductService productService;

   @Autowired
   private ProductRepository productRepository;

   @Autowired
   private B2BMemberRepository b2BMemberRepository;

   @Autowired
   private BCryptPasswordEncoder passwordEncoder;

   @BeforeEach
   public void setUp() {
       // 상품을 등록하기 위한 멤버 생성
       String password = passwordEncoder.encode("password1");
       B2BMember createMember = B2BMember.createMember(
               "seller1@email.com",
               password,
               "판매자1");
       b2BMemberRepository.save(createMember);

       // ON SALE 상태의 상품 15개 DB에 생성
       for (int i = 0; i < 15; i++) {
           String productName = String.format("상품%03d", i + 1);
           productRepository.save(Product.createProduct(
                   productName,
                   "상품 설명",
                   100,
                   100 * (i + 1),
                   ProductStatus.ON_SALE,
                   Category.TOP,
                   Category.SubCategory.T_SHIRT,
                   createMember
           ));
       }

       // PENDING 상태의 상품 한 개 DB에 생성
       productRepository.save(Product.createProduct(
               "미판매 상품",
               "판매 준비 중 입니다.",
               100,
               10000,
               ProductStatus.PENDING,
               Category.TOP,
               Category.SubCategory.T_SHIRT,
               createMember
       ));

       // OFF_SALE 상태의 상품 한 개 DB에 생성
       productRepository.save(Product.createProduct(
               "미판매 상품2",
               "판매 중비된 상품 입니다.",
               100,
               10000,
               ProductStatus.OFF_SALE,
               Category.TOP,
               Category.SubCategory.T_SHIRT,
               createMember
       ));
   }


   @Test
   @DisplayName("상품 검색 시 정렬이 잘 되어 있는지 테스트")
   public void searchProductTest1() {
       // given
       ProductSearchRequest reqDto = new ProductSearchRequest(
               1,
               10,
               "상품",
               "DESC",
               "name",
               Category.TOP,
               Category.SubCategory.T_SHIRT
       );

       // when
       PageProductResponse resDto = productService.searchProducts(reqDto);

       // then
       // 이름순 내림차순 정렬 하면 첫번째는 상품015, 10번째는 상품006이 조회되어야 함
       assertThat(resDto.contents().get(0).id()).isEqualTo(15L);
       assertThat(resDto.contents().get(0).name()).isEqualTo("상품015");
       assertThat(resDto.contents().get(1).id()).isEqualTo(14L);
       assertThat(resDto.contents().get(1).name()).isEqualTo("상품014");
       assertThat(resDto.contents().get(9).id()).isEqualTo(6L);
       assertThat(resDto.contents().get(9).name()).isEqualTo("상품006");
   }


   @Test
   @DisplayName("검색된 상품이 판매중인 상품이 아니라면 검색결과에 포함되지 않는 테스트")
   public void searchProductTest2() {
       // given
       ProductSearchRequest reqDto = new ProductSearchRequest(
               1,
               10,
               "미판매",
               "ASC",
               "name",
               Category.TOP,
               Category.SubCategory.T_SHIRT
       );
       // when
       PageProductResponse resDto = productService.searchProducts(reqDto);

       assertThat(resDto.contents().stream()
               .map(ProductResponse::status)
               .toList()).doesNotContainAnyElementsOf(Arrays.asList(ProductStatus.PENDING, ProductStatus.OFF_SALE));
       assertThat(resDto.contents()).hasSize(0);
       assertThat(resDto.totalPage()).isEqualTo(0);
   }

   @Test
   @DisplayName("페이지네이션이 작동하는지 확인 테스트")
   public void searchProductTest3() {
       // given
       ProductSearchRequest reqDto = new ProductSearchRequest(
               3,
               5,
               "상품",
               "ASC",
               "name",
               Category.TOP,
               Category.SubCategory.T_SHIRT
       );

       // when
       PageProductResponse resDto = productService.searchProducts(reqDto);

       // then
       // 17개중 ON_SALE은 15개 이므로 size: 5라면 3페이지 5개여야 함
       assertThat(resDto.totalPage()).isEqualTo(3);
       assertThat(resDto.contents()).hasSize(5);

   }
}

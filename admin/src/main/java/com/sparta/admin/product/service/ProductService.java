package com.sparta.admin.product.service;

import com.sparta.admin.product.dto.request.ProductRequest;
import com.sparta.admin.product.dto.response.PageProductResponse;
import com.sparta.impostor.commerce.backend.domain.product.entity.Product;
import com.sparta.impostor.commerce.backend.domain.product.enums.Category;
import com.sparta.impostor.commerce.backend.domain.product.enums.Category.SubCategory;
import com.sparta.impostor.commerce.backend.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  public PageProductResponse getAllProducts(ProductRequest productRequest) {

    Pageable pageable = PageRequest.of(
        productRequest.page() - 1,
        productRequest.size(),
        productRequest.orderBy().equalsIgnoreCase("asc") ?
            Sort.by(productRequest.sortBy()).ascending()
            : Sort.by(productRequest.sortBy()).descending()
    );

    Page<Product> products = productRepository.searchProductsWithFilters(
        "",
        productRequest.status(),
        Category.DEFAULT,
        SubCategory.DEFAULT,
        pageable
    );
    return PageProductResponse.from(products);

  }
}

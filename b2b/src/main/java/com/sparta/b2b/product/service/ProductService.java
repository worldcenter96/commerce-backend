package com.sparta.b2b.product.service;

import com.sparta.b2b.product.dto.request.ProductCreateRequest;
import com.sparta.b2b.product.dto.response.ProductCreateResponse;
import com.sparta.impostor.commerce.backend.domain.b2bMember.entity.B2BMember;
import com.sparta.impostor.commerce.backend.domain.b2bMember.enums.B2BMemberStatus;
import com.sparta.impostor.commerce.backend.domain.b2bMember.repository.B2BMemberRepository;
import com.sparta.impostor.commerce.backend.domain.product.entity.Product;
import com.sparta.impostor.commerce.backend.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

	private final ProductRepository productRepository;
	private final B2BMemberRepository b2bMemberRepository;

	public ProductCreateResponse createProduct(Long id, ProductCreateRequest request) {

		B2BMember member = b2bMemberRepository.findById(1l)
			.orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 멤버가 존재하지 않습니다."));

		if (member.getB2BMemberStatus() != B2BMemberStatus.ACTIVE) {
			throw new IllegalStateException("승인된 멤버만 상품을 등록할 수 있습니다.");
		}

		Product saveedProduct = productRepository.save(request.from());

		return ProductCreateResponse.from(saveedProduct);
	}
}

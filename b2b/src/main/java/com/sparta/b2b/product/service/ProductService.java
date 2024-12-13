package com.sparta.b2b.product.service;

import com.sparta.b2b.fileUpload.dto.ImageInfo;
import com.sparta.b2b.product.dto.request.ProductCreateRequest;
import com.sparta.b2b.product.dto.request.ProductUpdateRequest;
import com.sparta.b2b.product.dto.response.PageProductResponse;
import com.sparta.b2b.product.dto.response.ProductCreateResponse;
import com.sparta.b2b.product.dto.response.ProductSearchResponse;
import com.sparta.b2b.product.dto.response.ProductUpdateResponse;
import com.sparta.common.dto.MemberSession;
import com.sparta.impostor.commerce.backend.common.exception.AuthenticationFailedException;
import com.sparta.impostor.commerce.backend.common.exception.ForbiddenAccessException;
import com.sparta.impostor.commerce.backend.domain.b2bMember.entity.B2BMember;
import com.sparta.impostor.commerce.backend.domain.b2bMember.enums.B2BMemberStatus;
import com.sparta.impostor.commerce.backend.domain.b2bMember.repository.B2BMemberRepository;
import com.sparta.impostor.commerce.backend.domain.image.entity.Image;
import com.sparta.impostor.commerce.backend.domain.image.repository.ImageRepository;
import com.sparta.impostor.commerce.backend.domain.product.entity.Product;
import com.sparta.impostor.commerce.backend.domain.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

	private final ProductRepository productRepository;
	private final B2BMemberRepository b2bMemberRepository;
	private final ImageRepository imageRepository;


	public ProductCreateResponse createProduct(Long memberId, ProductCreateRequest request) {
		B2BMember member = b2bMemberRepository.findById(memberId)
			.orElseThrow(() -> new EntityNotFoundException("해당 ID를 가진 멤버가 존재하지 않습니다."));

		if (member.getB2BMemberStatus() != B2BMemberStatus.ACTIVE) {
			throw new ForbiddenAccessException("승인된 멤버만 상품 등록 할 수 있습니다.");
		}

		Product saveedProduct = productRepository.save(request.toProductEntity(member));

		List<ImageInfo> imageinfos = request.images();
		List<Image> images = new ArrayList<>();

		for (ImageInfo imageinfo : imageinfos) {
			Image image = Image.builder()
				.img_url(imageinfo.getUrl())
				.product(saveedProduct).build();

			images.add(image);
		}

		List<Image> savedImageList = imageRepository.saveAll(images);

		return ProductCreateResponse.from(saveedProduct, savedImageList);
	}

	@Transactional(readOnly = true)
	public ProductSearchResponse searchProduct(Long memberId, Long productId) {
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new EntityNotFoundException("해당 ID를 가진 상품이 존재하지 않습니다."));

		return ProductSearchResponse.from(product);
	}

	public PageProductResponse totalSearchProduct(
		Long memberId, int page, int size, String orderBy, String sortBy
	) {
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.fromString(orderBy), sortBy));
		Page<Product> productPage = productRepository.findAllByMemberId(memberId, pageable);//memberId 인덱싱 처리 성능 올리기

		return PageProductResponse.from(productPage);
	}

	public ProductUpdateResponse updateProduct(Long memberId, Long productId, ProductUpdateRequest request) {
		Product existingProduct = productRepository.findById(productId)
			.orElseThrow(() -> new EntityNotFoundException("해당 ID를 가진 상품이 존재하지 않습니다."));

		Product updatedProduct = Product.updateProduct(existingProduct, request.stockQuantity());
		Product savedProduct = productRepository.save(updatedProduct);

		return ProductUpdateResponse.from(savedProduct);
	}

	public void deleteProduct(Long memberId, Long productId) {
		B2BMember member = b2bMemberRepository.findById(memberId)
			.orElseThrow(() -> new AuthenticationFailedException("해당 ID를 가진 멤버가 존재하지 않습니다."));

		if (member.getB2BMemberStatus() != B2BMemberStatus.ACTIVE) {
			throw new ForbiddenAccessException("승인된 멤버만 삭제할 수 있습니다.");
		}
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new EntityNotFoundException("해당 제품을 찾을 수 없습니다."));

		if (product.getMember().getId() != member.getId()) {
			throw new ForbiddenAccessException("본인이 등록한 상품만 삭제할 수 있습니다.");
		}
		productRepository.deleteById(productId);
	}

}

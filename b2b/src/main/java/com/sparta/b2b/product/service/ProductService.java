package com.sparta.b2b.product.service;

import com.sparta.b2b.fileUpload.dto.ImageUploadedResponse;
import com.sparta.b2b.fileUpload.dto.ImageInfo;
import com.sparta.b2b.fileUpload.service.FileManageService;
import com.sparta.b2b.product.dto.request.ProductCreateRequest;
import com.sparta.b2b.product.dto.request.ProductUpdateRequest;
import com.sparta.b2b.product.dto.response.PageProductResponse;
import com.sparta.b2b.product.dto.response.ProductCreateResponse;
import com.sparta.b2b.product.dto.response.ProductSearchResponse;
import com.sparta.b2b.product.dto.response.ProductUpdateResponse;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

	private final ProductRepository productRepository;
	private final B2BMemberRepository b2bMemberRepository;
	private final ImageRepository imageRepository;
	private final FileManageService fileManageService;


	public ProductCreateResponse createProduct(Long memberId, ProductCreateRequest request, List<MultipartFile> productImageFiles) {
		ImageUploadedResponse imageUploadedResponse = fileManageService.uploadFiles(productImageFiles);

		B2BMember member = b2bMemberRepository.findById(memberId)
			.orElseThrow(() -> new EntityNotFoundException("해당 ID를 가진 멤버가 존재하지 않습니다."));

		if (member.getB2BMemberStatus() != B2BMemberStatus.ACTIVE) {
			throw new ForbiddenAccessException("승인된 멤버만 상품 등록 할 수 있습니다.");
		}
		Product saveedProduct = productRepository.save(request.toProductEntity(member));

		List<ImageInfo> imageinfos = imageUploadedResponse.getImages();
		List<Image> images = new ArrayList<>();

		for (ImageInfo imageinfo : imageinfos) {
			Image image = Image.builder()
				.imgUrl(imageinfo.getUrl())
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

		List<Image> images = imageRepository.findByProductId(productId);

		List<ImageInfo> imageInfos = new ArrayList<>();
		for (Image image : images) {
			ImageInfo imageInfo = new ImageInfo(image.getImgUrl());
			imageInfos.add(imageInfo);
		}
		return ProductSearchResponse.fromAndImages(product,imageInfos);
	}

	@Transactional(readOnly = true)
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

		existingProduct.update(request.stockQuantity());
		Product savedProduct = productRepository.save(existingProduct);

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

		// 이미지 삭제
		List<Image> allByProduct = imageRepository.findAllByProduct(product);
		for (Image image : allByProduct) {
			fileManageService.delete(image.getImgUrl()); // S3 에서 삭제
			imageRepository.delete(image); // DB에서 삭제
		}

		productRepository.deleteById(productId);
	}
}

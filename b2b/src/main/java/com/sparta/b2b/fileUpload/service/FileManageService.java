package com.sparta.b2b.fileUpload.service;

import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.sparta.b2b.fileUpload.dto.ImageUploadedResponse;
import com.sparta.b2b.fileUpload.dto.ImageInfo;
import com.sparta.b2b.fileUpload.dto.ImageUploadedResponseV2;
import com.sparta.impostor.commerce.backend.common.exception.ForbiddenAccessException;
import com.sparta.impostor.commerce.backend.domain.b2bMember.entity.B2BMember;
import com.sparta.impostor.commerce.backend.domain.b2bMember.enums.B2BMemberStatus;
import com.sparta.impostor.commerce.backend.domain.b2bMember.repository.B2BMemberRepository;
import com.sparta.impostor.commerce.backend.domain.image.entity.Image;
import com.sparta.impostor.commerce.backend.domain.image.repository.ImageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FileManageService {

	private final S3ManageService s3ManageService;
	private final ImageRepository imageRepository;
	private final B2BMemberRepository b2bMemberRepository;

	public ImageUploadedResponse uploadFiles(List<MultipartFile> files) {
		validateFiles(files);
		// 각 파일 업로드를 병렬 스트림으로 처리
		List<ImageInfo> infos = files.parallelStream()
			.map(file -> {
				try {
					String url = s3ManageService.upload(file);
					return new ImageInfo(url);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			})
			.collect(Collectors.toList());

		return ImageUploadedResponse.of(infos);
	}


	public void validateFiles(List<MultipartFile> files) {
		if (files == null || files.isEmpty()) {
			throw new EntityNotFoundException("파일 목록이 비어있습니다.");
		}
		if (files.size() > 10) {
			throw new IllegalArgumentException("최대 파일 갯수를 초과하였습니다.");
		}
		for (MultipartFile file : files) {
			if (file == null || file.isEmpty()) {
				throw new EntityNotFoundException("파일이 비어있거나 null입니다.");
			}
		}
	}


	public ImageUploadedResponseV2 uploadFileV2(MultipartFile file, Long memberId) {
		B2BMember member = b2bMemberRepository.findById(memberId)
			.orElseThrow(() -> new EntityNotFoundException("해당 ID를 가진 멤버가 존재하지 않습니다."));

		if (member.getB2BMemberStatus() != B2BMemberStatus.ACTIVE) {
			throw new ForbiddenAccessException("승인된 멤버만 상품 등록 할 수 있습니다.");
		}

		String imageUrl;
		try {
			imageUrl = s3ManageService.upload(file);
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
		Image image = Image.of(imageUrl);
		Image savedImage = imageRepository.save(image);
		return ImageUploadedResponseV2.from(savedImage);
	}


	public void delete(String imageUrl) {
		s3ManageService.delete(imageUrl);
	}


	public void removeUnusedFiles() {
		List<Image> unUsedImages = imageRepository.findAllByProductIsNull();
		log.info("unUsedImages size : {}", unUsedImages.size());
		for (Image unUsedImage : unUsedImages) {
			log.info(unUsedImage.toString());
			// S3에서 삭제
			delete(unUsedImage.getImgUrl());
			// DB에서 삭제
			imageRepository.delete(unUsedImage);
		}
	}
}

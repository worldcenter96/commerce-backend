package com.sparta.b2b.fileUpload.service;

import com.sparta.b2b.fileUpload.dto.ImageUploadedResponse;
import com.sparta.b2b.fileUpload.dto.ImageInfo;
import com.sparta.b2b.fileUpload.dto.ImageUploadedResponseV2;
import com.sparta.impostor.commerce.backend.domain.image.entity.Image;
import com.sparta.impostor.commerce.backend.domain.image.repository.ImageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class FileManageService {

	private final S3ManageService s3ManageService;
	private final ImageRepository imageRepository;

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


	public ImageUploadedResponseV2 uploadFileV2(MultipartFile file, Long groupId) {
		String imageUrl;
		try {
			imageUrl = s3ManageService.upload(file);
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
		Image image = Image.of(imageUrl);
		// 이미지 갯수 체크하는 로직 추가 필요
		Image savedImage = imageRepository.save(image);
		return ImageUploadedResponseV2.from(savedImage);
	}


	public void delete(String imageUrl) {
		s3ManageService.delete(imageUrl);
	}
}

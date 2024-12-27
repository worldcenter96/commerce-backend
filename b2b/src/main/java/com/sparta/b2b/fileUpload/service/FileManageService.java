package com.sparta.b2b.fileUpload.service;

import com.sparta.b2b.fileUpload.dto.ImageUploadedResponse;
import com.sparta.b2b.fileUpload.dto.ImageInfo;
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
	private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // TODO : confg에 넣으면 좋을듯

	public ImageUploadedResponse uploadFiles(List<MultipartFile> files) {

		validateFiles(files);

		// 각 파일 업로드를 병렬 스트림으로 처리
		List<ImageInfo> infos = files.parallelStream()
			.map(file -> {
				try {
					String url = s3ManageService.upload(file);
					return new ImageInfo(url);
				} catch (IOException e) {
					throw new RuntimeException(e); // 예외를 RuntimeException으로 변환
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

			if (file.getSize() > MAX_FILE_SIZE) {
				throw new IllegalArgumentException(
					String.format("파일 크기가 초과되었습니다. 파일명: %s, 크기: %.2fMB (최대 허용 크기: %.2fMB)",
						file.getOriginalFilename(),
						file.getSize() / (1024.0 * 1024.0),
						MAX_FILE_SIZE / (1024.0 * 1024.0)
					)
				);
			}
		}
	}


	// 이미지 삭제 메서드
	public void delete(String imageUrl) {
		s3ManageService.delete(imageUrl);
	}
}

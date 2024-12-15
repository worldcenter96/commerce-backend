package com.sparta.b2b.fileUpload.service;

import com.sparta.b2b.fileUpload.dto.ImangeUploadedResponse;
import com.sparta.b2b.fileUpload.dto.ImageInfo;
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

	public ImangeUploadedResponse uploadFiles(List<MultipartFile> files) {
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

		return ImangeUploadedResponse.of(infos);
	}


	// 이미지 삭제 메서드
	public void delete(String imageUrl) {
		s3ManageService.delete(imageUrl);
	}
}

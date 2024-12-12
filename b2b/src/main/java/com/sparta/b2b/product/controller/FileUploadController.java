package com.sparta.b2b.product.controller;

import com.sparta.b2b.product.dto.response.ApiResponse;
import com.sparta.b2b.product.service.S3Upload;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController("/api")
public class FileUploadController {

	private final S3Upload s3Upload;

	// 다건 파일 업로드
	@PostMapping("/products/images")
	public ApiResponse uploadFile( @RequestParam("images") List<MultipartFile> files ) throws IOException {

		// files
		for (MultipartFile file : files) {
			String url = s3Upload.upload(file);
		}





		// 파일업로드 서비스로직 예정

		return ApiResponse
		.builder()
		.build();
	}

}

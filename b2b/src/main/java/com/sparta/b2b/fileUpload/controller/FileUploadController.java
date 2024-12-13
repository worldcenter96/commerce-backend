package com.sparta.b2b.fileUpload.controller;

import com.sparta.b2b.fileUpload.dto.ApiResponse;
import com.sparta.b2b.fileUpload.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class FileUploadController {

	private final FileUploadService fileUploadService;

	// 다건 파일 업로드
	@PostMapping("/products/images")
	public ApiResponse uploadFiles(@RequestParam("images") List<MultipartFile> files) throws IOException {
		// 파일업로드 서비스로직
		ApiResponse apiResponse = fileUploadService.uploadFiles(files);
		return apiResponse;
	}

}

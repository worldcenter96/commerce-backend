package com.sparta.b2b.fileUpload.controller;

import com.sparta.b2b.fileUpload.dto.ImangeUploadedResponse;
import com.sparta.b2b.fileUpload.service.FileUploadService;
import com.sparta.common.annotation.CheckAuth;
import com.sparta.common.enums.Role;
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

	@CheckAuth(role = Role.B2B)
	@PostMapping("/products/images")
	public ImangeUploadedResponse uploadFiles(@RequestParam("images") List<MultipartFile> files) throws IOException {//images 갯수 제한(과금우려), 용량 제한 필수로 지정해야함
		// 파일업로드 서비스로직
		ImangeUploadedResponse apiResponse = fileUploadService.uploadFiles(files);
		return apiResponse;
	}
	// 파일 업로드 api 호출 -> url 포함하여 상품등록 api 호출
	// 상품 등록시 이미지 등록이 같이 되도록
}

package com.sparta.b2b.fileUpload.controller;

import com.sparta.b2b.fileUpload.dto.ImageUploadedResponse;
import com.sparta.b2b.fileUpload.dto.ImageUploadedResponseV2;
import com.sparta.b2b.fileUpload.service.FileManageService;
import com.sparta.common.annotation.CheckAuth;
import com.sparta.common.annotation.LoginMember;
import com.sparta.common.dto.MemberSession;
import com.sparta.common.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class FileUploadController {

	private final FileManageService fileUploadService;

	@Deprecated
	@CheckAuth(role = Role.B2B)
	@PostMapping("/products/images")
	public ImageUploadedResponse uploadFiles(@RequestParam("images") List<MultipartFile> files) throws IOException {

		ImageUploadedResponse apiResponse = fileUploadService.uploadFiles(files);
		return apiResponse;
	}


	@CheckAuth(role = Role.B2B)
	@PostMapping("/products/image")
	public ResponseEntity<ImageUploadedResponseV2> uploadFileV2(
		@RequestParam("image") MultipartFile file,
		@LoginMember(role = Role.B2B) MemberSession memberSession
	) {
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(fileUploadService.uploadFileV2(file, memberSession.memberId()));
	}
}

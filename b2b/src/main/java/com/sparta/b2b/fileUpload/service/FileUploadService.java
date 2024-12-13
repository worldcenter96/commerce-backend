package com.sparta.b2b.fileUpload.service;

import com.sparta.b2b.fileUpload.dto.ApiResponse;
import com.sparta.b2b.fileUpload.dto.ImageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadService {

	private final S3Upload s3Upload;

	public ApiResponse uploadFiles(List<MultipartFile> files) throws IOException {
		// files
		List<ImageInfo> infos= new ArrayList<>();

		for (MultipartFile file : files) {
			String url = s3Upload.upload(file);
			infos.add(new ImageInfo(url));
		}

		ApiResponse apiResponse = ApiResponse.of(infos);
		return apiResponse;
	}
}

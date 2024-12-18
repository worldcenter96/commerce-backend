package com.sparta.b2b.fileUpload.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@Service
public class S3ManageService {

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	private final AmazonS3 amazonS3;

	public String upload(MultipartFile multipartFile) throws IOException {
		//고유한번호 생성(램덤번호생성) 실제이름 보단 임의로 지정 될수 있도록//
		String s3FileName = "product-image/" + UUID.randomUUID().toString() + ".png";

		ObjectMetadata objMeta = new ObjectMetadata();

		amazonS3.putObject(new PutObjectRequest(bucket, s3FileName, multipartFile.getInputStream(), objMeta)
			.withCannedAcl(CannedAccessControlList.PublicRead));

		String url = amazonS3.getUrl(bucket, s3FileName).toString();

		return url;
	}

	public void delete(String imageUrl) {
		// URL에서 파일 이름 추출
		String fileName = extractFileNameFromUrl(imageUrl);
		try {
			amazonS3.deleteObject(bucket, fileName);
			log.info("S3에서 파일 삭제 완료: {}", fileName);
		} catch (Exception e) {
			log.error("S3에서 파일 삭제 실패: {}", fileName, e);
		}
	}

	// URL에서 S3 파일 경로 추출 // s3 + fileName
	private String extractFileNameFromUrl(String imageUrl) {
		String bucketUrl = amazonS3.getUrl(bucket, "").toString();

		if (!imageUrl.startsWith(bucketUrl)) {
			log.error("유효하지 않은 파일 URL: {}", imageUrl);
		}

		return imageUrl.substring(bucketUrl.length());
	}

}

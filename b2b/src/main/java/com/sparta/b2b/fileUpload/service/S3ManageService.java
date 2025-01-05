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
		String fileName = multipartFile.getOriginalFilename();
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

		if (!(ext.equals("jpg") || ext.equals("jpeg") || ext.equals("png"))) {
			throw new IllegalArgumentException("지원되지 않는 확장자입니다. jpg, jpeg, png만 업로드 가능합니다.");
		}

		String contentType = multipartFile.getContentType();
		String s3FileName = "product-image/" + UUID.randomUUID() + "." + ext;

		ObjectMetadata objMeta = new ObjectMetadata();
		objMeta.setContentType(contentType);
		objMeta.setContentLength(multipartFile.getSize());

		amazonS3.putObject(new PutObjectRequest(bucket, s3FileName, multipartFile.getInputStream(), objMeta)
			.withCannedAcl(CannedAccessControlList.PublicRead));

		String url = amazonS3.getUrl(bucket, s3FileName).toString();

//		https://impostor-s3bucket.s3.ap-northeast-2.amazonaws.com/product-image/0a727907-8738-4bd0-ad82-f564a63fd411.jpg
		return url;
	}



	public void delete(String imageUrl) {

		log.info("imageUrl : {}" , imageUrl);

		String[] split = imageUrl.split("/");
		String fileName = split[split.length - 1].trim();

		log.info("fileName : {}" , imageUrl);

		try {

			amazonS3.deleteObject(bucket, "product-image/" + fileName);
			log.info("S3에서 파일 삭제 완료: {}", fileName);

		} catch (Exception e) {
			log.error("S3에서 파일 삭제 실패: {}", fileName, e);
		}
	}


	private String extractFileNameFromUrl(String imageUrl) {
		String bucketUrl = amazonS3.getUrl(bucket, "").toString();
		if (!imageUrl.startsWith(bucketUrl)) {
			log.error("유효하지 않은 파일 URL: {}", imageUrl);
		}
		return imageUrl.substring(bucketUrl.length());
	}
}

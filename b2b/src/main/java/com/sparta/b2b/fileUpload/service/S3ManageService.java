package com.sparta.b2b.fileUpload.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
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

		return url;
	}



	public void delete(String imageUrl) {
		log.info("1111111111111111111111111");

		String[] split = imageUrl.split("/");
		String fileName = split[split.length - 1];

		log.info("222222");
		try {

			log.info("3333");

			amazonS3.deleteObject(bucket, fileName);

			log.info("444");
			log.info("S3에서 파일 삭제 완료: {}", fileName);
			log.info("5555");

		} catch (Exception e) {
			log.info("6666");
			log.error("S3에서 파일 삭제 실패: {}", fileName, e);
			log.info("77777");
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

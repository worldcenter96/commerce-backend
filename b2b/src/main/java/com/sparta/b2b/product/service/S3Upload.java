package com.sparta.b2b.product.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class S3Upload {

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	private final AmazonS3 amazonS3;

	public String upload(MultipartFile multipartFile) throws IOException {
		//고유한번호 생성(램덤번호생성)
		String s3FileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

		// ObjectMetadata 객체 생성: 파일의 메타데이터를 설정하기 위한 객체
		ObjectMetadata objMeta = new ObjectMetadata();

		// 파일의 크기(Content-Length)를 메타데이터에 설정
		// multipartFile.getInputStream().available()는 파일의 크기를 반환
		objMeta.setContentLength(multipartFile.getInputStream().available());


		// AWS S3에 파일 업로드
		// bucket: 업로드할 S3 버킷 이름
		// s3FileName: S3에 저장될 파일 이름
		// multipartFile.getInputStream(): 업로드할 파일의 입력 스트림
		// objMeta: 파일의 메타데이터 객체 (파일 크기 및 기타 정보 포함)
		amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);
		String url = amazonS3.getUrl(bucket, s3FileName).toString();

		return url;
	}

}

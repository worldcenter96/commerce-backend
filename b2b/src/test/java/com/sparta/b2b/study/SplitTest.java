package com.sparta.b2b.study;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SplitTest {

	@DisplayName("파일명 추출 테스트")
	@Test
	void fileNameSplitTest() {

		// 1. S3 https://impostor-s3bucket.s3.ap-northeast-2.amazonaws.com/product-image/0f55c81b-2fe0-47d9-a242-6244aab09ed8.jpg
		// 2 로컬스텍 http://localhost:4566/local-bucket/product-image/bf79c0dd-b45c-4c58-b251-92c1d275d77b.jpg
		String s3FileUrl = "https://impostor-s3bucket.s3.ap-northeast-2.amazonaws.com/product-image/0f55c81b-2fe0-47d9-a242-6244aab09ed8.jpg";
		String localStackFileUrl = "http://localhost:4566/local-bucket/product-image/bf79c0dd-b45c-4c58-b251-92c1d275d77b.jpg";

		String[] s3Split = s3FileUrl.split("/");
		String s3FileName = s3Split[s3Split.length - 1];

		String[] localStackSplit = localStackFileUrl.split("/");
		String localStackFileName = localStackSplit[localStackSplit.length - 1];

		Assertions.assertThat(s3FileName).isEqualTo("0f55c81b-2fe0-47d9-a242-6244aab09ed8.jpg");
		Assertions.assertThat(localStackFileName).isEqualTo("bf79c0dd-b45c-4c58-b251-92c1d275d77b.jpg");
	}
}

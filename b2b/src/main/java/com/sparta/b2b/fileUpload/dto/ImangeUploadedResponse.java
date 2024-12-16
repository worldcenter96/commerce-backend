package com.sparta.b2b.fileUpload.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImangeUploadedResponse {// 이름수정

	public List<ImageInfo> images;

	public static ImangeUploadedResponse of(List<ImageInfo> infos) {
		return ImangeUploadedResponse.builder()
		.images(infos)
		.build();
	}
}


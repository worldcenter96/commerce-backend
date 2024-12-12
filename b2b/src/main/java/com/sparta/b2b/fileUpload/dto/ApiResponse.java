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
public class ApiResponse {

	public List<ImageInfo> images;

	public static ApiResponse of(List<ImageInfo> infos) {
		return ApiResponse.builder()
		.images(infos)
		.build();
	}
}

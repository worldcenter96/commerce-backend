package com.sparta.b2b.product.dto.response;

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

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ImageInfo {
		private String url;
		private String value;
	}
}

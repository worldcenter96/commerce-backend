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
public class ImageUploadedResponse {

	public List<ImageInfo> images;

	public static ImageUploadedResponse of(List<ImageInfo> infos) {
		return ImageUploadedResponse.builder()
		.images(infos)
		.build();
	}
}


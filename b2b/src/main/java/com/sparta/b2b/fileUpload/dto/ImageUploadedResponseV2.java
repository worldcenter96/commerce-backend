package com.sparta.b2b.fileUpload.dto;

import com.sparta.impostor.commerce.backend.domain.image.entity.Image;

public record ImageUploadedResponseV2 (
	Long imageId,
	String url
) {

	public static ImageUploadedResponseV2 from(Image savedImage) {
		return new ImageUploadedResponseV2(
			savedImage.getId(),
			savedImage.getImgUrl()
		);
	}
}



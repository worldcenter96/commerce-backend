package com.sparta.b2b.fileUpload;

import com.sparta.b2b.fileUpload.service.FileManageService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileValidationTest {

	@Test
	void validateFiles_shouldThrowException_whenFilesAreNull() {
		// given
		FileManageService fileManageService = new FileManageService(null);

		// when
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
			() -> fileManageService.validateFiles(null));

		// then
		assertEquals("파일 목록이 비어있습니다.", exception.getMessage());
	}

	@Test
	void validateFiles_shouldThrowException_whenFilesAreEmpty() {
		FileManageService fileManageService = new FileManageService(null);

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
			() -> fileManageService.validateFiles(Collections.emptyList()));

		assertEquals("파일 목록이 비어있습니다.", exception.getMessage());
	}

	@Test
	void validateFiles_shouldThrowException_whenFileCountExceedsLimit() {
		FileManageService fileManageService = new FileManageService(null);

		List<MultipartFile> files = new ArrayList<>();
		for (int i = 0; i < 11; i++) {
			files.add(Mockito.mock(MultipartFile.class));
		}

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
			() -> fileManageService.validateFiles(files));

		assertEquals("최대 파일 갯수를 초과하였습니다.", exception.getMessage());
	}

	@Test
	void validateFiles_shouldThrowException_whenFileIsNullOrEmpty() {
		FileManageService fileManageService = new FileManageService(null);

		MultipartFile emptyFile = Mockito.mock(MultipartFile.class);
		Mockito.when(emptyFile.isEmpty()).thenReturn(true);

		List<MultipartFile> files = List.of(emptyFile);

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
			() -> fileManageService.validateFiles(files));

		assertEquals("파일이 비어있거나 null입니다.", exception.getMessage());
	}

	@Test
	void validateFiles_shouldThrowException_whenFileSizeExceedsLimit() {
		FileManageService fileManageService = new FileManageService(null);

		MultipartFile largeFile = Mockito.mock(MultipartFile.class);
		Mockito.when(largeFile.isEmpty()).thenReturn(false);
		Mockito.when(largeFile.getSize()).thenReturn(6 * 1024 * 1024L); // 6MB
		Mockito.when(largeFile.getOriginalFilename()).thenReturn("largeFile.jpg");

		List<MultipartFile> files = List.of(largeFile);

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
			() -> fileManageService.validateFiles(files));

		assertTrue(exception.getMessage().contains("파일 크기가 초과되었습니다."));
		assertTrue(exception.getMessage().contains("largeFile.jpg"));
	}

	@Test
	void validateFiles_shouldPass_whenAllFilesAreValid() {
		FileManageService fileManageService = new FileManageService(null);

		MultipartFile validFile = Mockito.mock(MultipartFile.class);
		Mockito.when(validFile.isEmpty()).thenReturn(false);
		Mockito.when(validFile.getSize()).thenReturn(4 * 1024 * 1024L); // 4MB
		Mockito.when(validFile.getOriginalFilename()).thenReturn("validFile.jpg");

		List<MultipartFile> files = List.of(validFile);

		assertDoesNotThrow(() -> fileManageService.validateFiles(files));
	}
}

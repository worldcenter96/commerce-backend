package com.sparta.b2b.fileUpload;

import com.sparta.b2b.fileUpload.service.FileManageService;

import jakarta.persistence.EntityNotFoundException;
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
		FileManageService fileManageService = new FileManageService(null,null, null);

		// when
		EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
			() -> fileManageService.validateFiles(null));

		// then
		assertEquals("파일 목록이 비어있습니다.", exception.getMessage());
	}

	@Test
	void validateFiles_shouldThrowException_whenFilesAreEmpty() {
		FileManageService fileManageService = new FileManageService(null,null, null);

		EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
			() -> fileManageService.validateFiles(Collections.emptyList()));

		assertEquals("파일 목록이 비어있습니다.", exception.getMessage());
	}

	@Test
	void validateFiles_shouldThrowException_whenFileCountExceedsLimit() {
		FileManageService fileManageService = new FileManageService(null,null, null);

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
		FileManageService fileManageService = new FileManageService(null,null, null);

		MultipartFile emptyFile = Mockito.mock(MultipartFile.class);
		Mockito.when(emptyFile.isEmpty()).thenReturn(true);

		List<MultipartFile> files = List.of(emptyFile);

		EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
			() -> fileManageService.validateFiles(files));

		assertEquals("파일이 비어있거나 null입니다.", exception.getMessage());
	}

	@Test
	void validateFiles_shouldPass_whenAllFilesAreValid() {
		FileManageService fileManageService = new FileManageService(null,null, null);

		MultipartFile validFile = Mockito.mock(MultipartFile.class);
		Mockito.when(validFile.isEmpty()).thenReturn(false);
		Mockito.when(validFile.getSize()).thenReturn(4 * 1024 * 1024L); // 4MB
		Mockito.when(validFile.getOriginalFilename()).thenReturn("validFile.jpg");

		List<MultipartFile> files = List.of(validFile);

		assertDoesNotThrow(() -> fileManageService.validateFiles(files));
	}
}

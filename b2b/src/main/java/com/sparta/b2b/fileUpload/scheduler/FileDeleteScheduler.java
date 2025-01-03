package com.sparta.b2b.fileUpload.scheduler;

import com.sparta.b2b.fileUpload.service.FileManageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class FileDeleteScheduler {

	private final FileManageService fileManageService;

	@Scheduled(cron = "0 0 23 * * *")
	public void removeUnusedFiles() {

		fileManageService.removeUnusedFiles();
		log.info("call - removeUnusedFiles()");
	}
}


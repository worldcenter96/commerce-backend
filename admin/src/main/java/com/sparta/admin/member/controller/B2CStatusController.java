package com.sparta.admin.member.controller;

import com.sparta.admin.member.dto.request.B2CStatusRequest;
import com.sparta.admin.member.dto.response.B2CStatusResponse;
import com.sparta.admin.member.service.B2CStatusService;
import com.sparta.common.annotation.CheckAuth;
import com.sparta.common.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class B2CStatusController {

  private final B2CStatusService b2cStatusService;

  @CheckAuth(role = Role.ADMIN)
  @PatchMapping("/b2c/{memberId}/update-status")
  public ResponseEntity<B2CStatusResponse> b2CStatusResponseResponseEntity(
      @PathVariable Long memberId,
      @RequestBody B2CStatusRequest request) {

    // 결과 응답 반환
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(b2cStatusService.updateB2CStatus(memberId, request));
  }

}

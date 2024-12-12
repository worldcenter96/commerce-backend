package com.sparta.admin.member.controller;

import com.sparta.admin.member.dto.request.B2BMemberStatusRequest;
import com.sparta.admin.member.dto.response.B2BApprovalResponse;
import com.sparta.admin.member.service.B2BApprovalService;
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
public class B2BApprovalController {

  private final B2BApprovalService b2bApprovalService;

  // B2B 회원 권한 요청 승인 또는 거절 (기본 PENDING 상태, 수락 및 거절시 ACTIVE, INACTIVE 상태로 변경)
  @CheckAuth(role = Role.ADMIN)
  @PatchMapping("/b2b/{memberId}/approve")
  public ResponseEntity<B2BApprovalResponse> approveOrRejectMember(@PathVariable Long memberId,
      @RequestBody B2BMemberStatusRequest request) {

    // 결과 응답 반환
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(b2bApprovalService.approveOrRejectMember(memberId, request));
  }
}


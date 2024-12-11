package com.sparta.admin.member.controller;

import com.sparta.admin.member.dto.response.B2CApprovalResponse;
import com.sparta.admin.member.service.B2CApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/b2c-members")
public class B2CApprovalController {

  private final B2CApprovalService b2cApprovalService;

  // B2C 회원 권한 요청 승인 또는 거절 (어드민이 승인 또는 거절 후 ACTIVE 상태로 변경)
  @PostMapping("/{memberId}/approve-reject")
  public ResponseEntity<B2CApprovalResponse> approveOrRejectMember(@PathVariable Long memberId) {
    // 승인 및 거절 처리
    B2CApprovalResponse response = b2cApprovalService.approveOrRejectMember(memberId);

    // 결과 응답 반환
    return ResponseEntity.ok(response);

    // 승인 및 거절 모두 ACTIVE,
    // INACTIVE 는 블랙리스트 처리 되었을 경우에만 적용됨
    // PENDING 은 권한 요청에 대한 응답이 대기중일때만 적용됨.
  }
}


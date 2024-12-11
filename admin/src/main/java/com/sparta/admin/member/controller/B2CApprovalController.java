package com.sparta.admin.member.controller;

import com.sparta.admin.member.dto.request.B2CMemberRequest;
import com.sparta.admin.member.service.B2CApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/b2c-members/approval")
public class B2CApprovalController {

  private final B2CApprovalService b2cApprovalService;

  // B2C 회원이 권한 요청 API
  @PostMapping("/request")
  public String requestPermission(@RequestBody B2CMemberRequest Request) {
    // 요청한 회원 ID를
    b2cApprovalService.requestPermisiion(request.memverId());
    return "권한 요청이 성공적으로 승인 되었습니다.";
  }

  // B2C 회원 승인 API
  @P
}

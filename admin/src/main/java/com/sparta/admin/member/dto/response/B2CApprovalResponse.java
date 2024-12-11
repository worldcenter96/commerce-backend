package com.sparta.admin.member.dto.response;

import com.sparta.impostor.commerce.backend.domain.b2cMember.enums.B2CMemberStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class B2CApprovalResponse {

  private Long memberId;
  private String message;
  private B2CMemberStatus status; // 승인된 후의 상태


  }


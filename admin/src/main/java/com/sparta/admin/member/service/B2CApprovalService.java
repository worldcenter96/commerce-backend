package com.sparta.admin.member.service;

import com.sparta.admin.member.dto.response.B2CApprovalResponse;
import com.sparta.impostor.commerce.backend.domain.b2cMember.entity.B2CMember;
import com.sparta.impostor.commerce.backend.domain.b2cMember.enums.B2CMemberStatus;
import com.sparta.impostor.commerce.backend.domain.b2cMember.repository.B2CMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class B2CApprovalService {

  private final B2CMemberRepository b2cMemberRepository;

  // 어드민이 B2C 회원 권한 요청 승인하거나 거절할 때, ACTIVE 로 설정
  public B2CApprovalResponse approveOrRejectMember(Long memberId) {
    B2CMember member = b2cMemberRepository.findById(memberId)
        .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

    // 상태를 ACTIVE 로 변경 (승인과 거절 모두 ACTIVE 로 설정)
    member.changeStatus(B2CMemberStatus.ACTIVE);
    b2cMemberRepository.save(member);

    return new B2CApprovalResponse(memberId, "회원의 권한 요청이 처리되었습니다.", B2CMemberStatus.ACTIVE);
  }

}

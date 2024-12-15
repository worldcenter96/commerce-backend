package com.sparta.admin.member.service;

import com.sparta.admin.member.dto.request.B2BMemberStatusRequest;
import com.sparta.admin.member.dto.response.B2BApprovalResponse;
import com.sparta.common.service.SessionService;
import com.sparta.impostor.commerce.backend.domain.b2bMember.entity.B2BMember;
import com.sparta.impostor.commerce.backend.domain.b2bMember.enums.B2BMemberStatus;
import com.sparta.impostor.commerce.backend.domain.b2bMember.repository.B2BMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class B2BApprovalService {

  private final B2BMemberRepository b2bMemberRepository;
  private final SessionService sessionService;
  private static final String SESSION_NAME = "B2B_SESSION";

  // 어드민이 B2B 회원 권한 요청 승인하거나 거절할 때, ACTIVE, INACTIVE 로 설정 (기본값 PENDING)
  public B2BApprovalResponse approveOrRejectMember(Long memberId, B2BMemberStatusRequest request) {

    B2BMemberStatus b2bMemberStatus = request.status();

    B2BMember member = b2bMemberRepository.findById(memberId)
        .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

    B2BMember updatedMember = member.changeStatus(b2bMemberStatus);

    if (updatedMember.getB2BMemberStatus() == B2BMemberStatus.INACTIVE) {
      sessionService.deleteSession(SESSION_NAME, updatedMember.getId());
    }

    return B2BApprovalResponse.from(updatedMember);
  }

}

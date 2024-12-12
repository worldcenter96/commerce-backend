package com.sparta.admin.member.service;

import com.sparta.admin.member.dto.request.B2CStatusRequest;
import com.sparta.admin.member.dto.response.B2CStatusResponse;
import com.sparta.impostor.commerce.backend.domain.b2cMember.entity.B2CMember;
import com.sparta.impostor.commerce.backend.domain.b2cMember.enums.B2CMemberStatus;
import com.sparta.impostor.commerce.backend.domain.b2cMember.repository.B2CMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class B2CStatusService {

  private final B2CMemberRepository b2cMemberRepository;

  // B2C 회원 ACTIVE, INACTIVE 로 상태 전환
  public B2CStatusResponse updateB2CStatus(Long memberId, B2CStatusRequest request) {

    B2CMemberStatus newStatus = request.status();

    B2CMember b2cMember = b2cMemberRepository.findById(memberId)
        .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

    B2CMember updatedMember = b2cMember.changeStatus(newStatus);

    return B2CStatusResponse.from(updatedMember);
  }

}

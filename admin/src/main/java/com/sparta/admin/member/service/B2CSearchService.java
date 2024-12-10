package com.sparta.admin.member.service;

import com.sparta.admin.member.dto.response.B2CMemberPageResponse;
import com.sparta.impostor.commerce.backend.domain.b2cMember.entity.B2CMember;
import com.sparta.impostor.commerce.backend.domain.b2cMember.enums.B2CMemberStatus;
import com.sparta.impostor.commerce.backend.domain.b2cMember.repository.B2CMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class B2CSearchService {

  private final B2CMemberRepository b2cMemberRepository;

  // B2C 회원 조회 ( 전체 조회, 상태별 조회, 페이지네이션, 정렬 적용)
  public B2CMemberPageResponse getB2CMembers(B2CMemberStatus status, PageRequest pageRequest) {
    Page<B2CMember> b2cMembers;

    // status 파라미터에 따라 필터링
    if (status != null) {
      // 상태에 맞는 회원들만 조회
      b2cMembers = b2cMemberRepository.findByB2CMemberStatus(status, pageRequest);
    } else {
      // 상태가 주어지지 않으면, 모든 회원 조회
      b2cMembers = b2cMemberRepository.findAll(pageRequest);
    }

    return new B2CMemberPageResponse(b2cMembers);
  }
}

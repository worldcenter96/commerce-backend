package com.sparta.admin.member.service;

import com.sparta.admin.member.dto.response.B2BMemberResponse;
import com.sparta.impostor.commerce.backend.domain.b2bMember.entity.B2BMember;
import com.sparta.impostor.commerce.backend.domain.b2bMember.enums.B2BMemberStatus;
import com.sparta.impostor.commerce.backend.domain.b2bMember.repository.B2BMemberRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class B2BSearchService {

  private final B2BMemberRepository b2bMemberRepository;

  // B2B 회원 조회 (전체 조회, 페이지네이션, 정렬 적용)
  public Page<B2BMemberResponse> getB2BMembers(PageRequest pageRequest) {
    // 모든 B2B 회원을 조회하여 Response 로 변환
    Page<B2BMember> b2bMemberPage = b2bMemberRepository.findAll(pageRequest);

    return b2bMemberPage.map(B2BMemberResponse::from);

  }

  // 특정 status 애 해당하는 B2B 회원 조회 (status: INACTIVE, ACTIVE, PENDING)
  public Page<B2BMemberResponse> getB2BMembersByStatus(
      B2BMemberStatus status,
      PageRequest pageRequest) {

    // 상태에 맞는 B2B 회원을 조회하여 Response 로 변환
    Page<B2BMember> b2bMemberPage = b2bMemberRepository.findByB2BMemberStatus(status, pageRequest);

    return b2bMemberPage.map(B2BMemberResponse::from);
  }

  // B2B 회원 상태 업데이트 ( status: ACTIVE, INACTIVE)
  public B2BMember updateB2BMemberStatus(Long b2bMemberId, B2BMemberStatus newStatus) {

    B2BMember b2bMember = b2bMemberRepository.findById(b2bMemberId)
        .orElseThrow(() -> new IllegalArgumentException("B2B 회원을 찾을 수 없습니다."));
  // 상태 변경
   b2bMember.setB2BMeberStatus(newStatus);

    return b2bMemberRepository.save(b2bMember);
  }

}
package com.sparta.admin.member.service;

import com.sparta.admin.member.dto.response.B2BMemberResponse;
import com.sparta.impostor.commerce.backend.domain.b2bMember.entity.B2BMember;
import com.sparta.impostor.commerce.backend.domain.b2bMember.enums.B2BMemberStatus;
import com.sparta.impostor.commerce.backend.domain.b2bMember.repository.B2BMemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class B2BSearchService {

  private final B2BMemberRepository b2bMemberRepository;

  // B2B 회원 조회 (페이지네이션, 정렬 적용)
  public Page<B2BMemberResponse> getB2BMembers(PageRequest pageRequest) {
    Page<B2BMember> b2bMemberPage = b2bMemberRepository.findAll(pageRequest);
    List<B2BMemberResponse> responses = b2bMemberPage.getContent().stream()
        .map(B2BMemberResponse::from)
        .toList();

    return b2bMemberPage.map(
        B2BMemberResponse::from); // Page<B2BMemberResponse> 반환

  }

  // 특정 status 애 해당하는 B2B 회원 조회 (예: INACTIVE)
  public Page<B2BMemberResponse> getB2BMembersByStatus(B2BMemberStatus status,
      PageRequest pageRequest) {
    Page<B2BMember> b2BMemberPage = b2bMemberRepository.findByB2BMemberStatus(status, pageRequest);

    return b2BMemberPage.map(B2BMemberResponse::from);
  }

  // B2B 회원 상태 업데이트
  public B2BMember updateMemberStatus(Long b2bMemberId, B2BMemberStatus newStatus) {
    B2BMember b2bMember = b2bMemberRepository.findById(b2bMemberId)
        .orElseThrow(() -> new RuntimeException("B2B Member Not Found"));

    B2BMember updatedB2BMember = b2bMember.changeStatus(newStatus);

    return b2bMemberRepository.save(updatedB2BMember);
  }

  public void updateB2BMemberStatus(Long b2bMemberId, B2BMemberStatus status) {
  }
}
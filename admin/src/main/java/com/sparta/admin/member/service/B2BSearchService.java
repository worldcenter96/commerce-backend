package com.sparta.admin.member.service;

import com.sparta.admin.member.dto.response.B2BMemberPageResponse;
import com.sparta.impostor.commerce.backend.domain.b2bMember.entity.B2BMember;
import com.sparta.impostor.commerce.backend.domain.b2bMember.enums.B2BMemberStatus;
import com.sparta.impostor.commerce.backend.domain.b2bMember.repository.B2BMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class B2BSearchService {

  private final B2BMemberRepository b2bMemberRepository;

  // B2B 회원 조회 (전체 조회, 페이지네이션, 정렬 적용)
  public B2BMemberPageResponse getB2BMembers(int page, int size, String sortBy, String orderBy) {

    // 정렬 방향 결정
    Sort.Direction direction =
        orderBy.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

    PageRequest pageRequest = PageRequest.of(page - 1, size, direction, sortBy);

    // 모든 B2B 회원을 조회하여 Response 로 변환
    Page<B2BMember> b2bMemberPage = b2bMemberRepository.findAll(pageRequest);

    return new B2BMemberPageResponse(b2bMemberPage);

  }

  // 특정 status 애 해당하는 B2B 회원 조회 (status: INACTIVE, ACTIVE, PENDING)
  public B2BMemberPageResponse getB2BMembersByStatus(
      int page, int size, String sortBy, String orderBy, B2BMemberStatus status) {

    Sort.Direction direction =
        orderBy.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

    PageRequest pageRequest = PageRequest.of(page - 1, size, direction, sortBy, orderBy);

    // 상태에 맞는 B2B 회원을 조회하여 Response 로 변환
    Page<B2BMember> b2bMemberPage = b2bMemberRepository.findByB2bMemberStatus(status, pageRequest);

    return new B2BMemberPageResponse(b2bMemberPage);
  }

}
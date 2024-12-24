package com.sparta.admin.member.service;

import com.sparta.admin.member.dto.response.B2BMemberPageResponse;
import com.sparta.admin.member.dto.response.B2CMemberPageResponse;
import com.sparta.impostor.commerce.backend.domain.b2bMember.entity.B2BMember;
import com.sparta.impostor.commerce.backend.domain.b2bMember.enums.B2BMemberStatus;
import com.sparta.impostor.commerce.backend.domain.b2cMember.entity.B2CMember;
import com.sparta.impostor.commerce.backend.domain.b2cMember.enums.B2CMemberStatus;
import com.sparta.impostor.commerce.backend.domain.b2cMember.repository.B2CMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class B2CSearchService {

  private final B2CMemberRepository b2cMemberRepository;

  // 전체 B2C 회원 조회 (전체 조회, 페이지네이션, 정렬 적용)
  public B2CMemberPageResponse getB2CMembers(int page, int size, String sortBy, String orderBy) {

    // 정렬 방향 결정
    Sort.Direction direction =
            orderBy.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

    PageRequest pageRequest = PageRequest.of(page - 1, size, direction, sortBy);

    // 모든 B2C 회원을 조회하여 Response 로 변환
    Page<B2CMember> b2cMemberPage = b2cMemberRepository.findAll(pageRequest);

    return new B2CMemberPageResponse(b2cMemberPage);

  }

  // 특정 status 애 해당하는 B2C 회원 조회 (status: INACTIVE, ACTIVE)
  public B2CMemberPageResponse getB2CMembersByStatus(
          int page, int size, String sortBy, String orderBy, B2CMemberStatus status) {

    Sort.Direction direction =
            orderBy.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

    PageRequest pageRequest = PageRequest.of(page - 1, size, direction, sortBy);

    // 상태에 맞는 B2C 회원을 조회하여 Response 로 변환
    Page<B2CMember> b2cMemberPage = b2cMemberRepository.findByB2cMemberStatus(status, pageRequest);

    return new B2CMemberPageResponse(b2cMemberPage);
  }
}

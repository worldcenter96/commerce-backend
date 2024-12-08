package com.sparta.admin.member.service;

import com.sparta.admin.member.dto.response.B2BMemberPageResponse;
import com.sparta.admin.member.dto.response.B2BMemberResponse;
import com.sparta.impostor.commerce.backend.domain.b2bMember.entity.B2BMember;
import com.sparta.impostor.commerce.backend.domain.b2bMember.repository.B2BMemberRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class B2BSearchService {

  private B2BMemberRepository b2bMemberRepository;

  public B2BMemberPageResponse getB2BMembers(
      int page,
      int size,
      String sortBy,
      String orderBy) {

    // 정렬 방향 설정
    Sort.Direction direction = orderBy.equalsIgnoreCase(
        "desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

    // 페이지 요청 및 정렬 처리
    Page<B2BMember> b2BMemberPage = b2bMemberRepository.findAll(
        PageRequest.of(page - 1, size, Sort.by(direction, sortBy))
    );

    // B2BMember를 B2BMemberResponse로 변환
    List<B2BMemberResponse> members = b2BMemberPage.stream()
        .map(B2BMemberResponse::from)
        .collect(Collectors.toList());

    return new B2BMemberPageResponse(
        members,
        b2BMemberPage.getNumber() + 1, // 현재 페이즈 (1-based index)
        b2BMemberPage.getSize(),
        sortBy, // 정렬 기준
        orderBy, // 정렬 순서
        b2BMemberPage.getTotalPages()
    );
  }
}
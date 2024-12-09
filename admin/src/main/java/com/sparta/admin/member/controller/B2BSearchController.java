package com.sparta.admin.member.controller;

import com.sparta.admin.member.dto.response.B2BMemberResponse;
import com.sparta.admin.member.service.B2BSearchService;
import com.sparta.impostor.commerce.backend.domain.b2bMember.enums.B2BMemberStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/b2b-members")
@RequiredArgsConstructor
public class B2BSearchController {

  private final B2BSearchService b2BSearchService;

  // B2B 회원 전체 조회
  @GetMapping
  public Page<B2BMemberResponse> getB2BMembers(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String orderBy
  ) {
    // 정렬 방향 결정
    Sort.Direction direction =
        orderBy.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

    PageRequest pageRequest = PageRequest.of(
        page - 1,
        size,
        Sort.by(direction, sortBy));

    return b2BSearchService.getB2BMembers(pageRequest);
  }


  // 미승인 사용자 리스트 조회 (status = INACTIVE)
  @GetMapping("/INACTIVE")
  public Page<B2BMemberResponse> getInactiveB2BMembers(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String orderBy
  ) {
    // 정렬 방향 결정
    Sort.Direction direction =
        orderBy.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
    // PageRequest 객체 생성
    PageRequest pageRequest = PageRequest.of(
        page - 1,
        size,
        Sort.by(direction, sortBy));

    return b2BSearchService.getB2BMembers(pageRequest);
  }

  // 판매자 권한 변경 (ACTIVE,INACTIVE)
  @PatchMapping("/{b2bMemberId}/status")
  public void updateB2BMemberStatus(
      @PathVariable Long b2bMemberId,
      @RequestBody B2BMemberStatus status) {
    b2BSearchService.updateB2BMemberStatus(b2bMemberId, status);
  }
}
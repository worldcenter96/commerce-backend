package com.sparta.admin.member.controller;

import com.sparta.admin.member.dto.request.B2BMemberStatusRequest;
import com.sparta.admin.member.dto.response.B2BMemberPageResponse;
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

  private final B2BSearchService b2bSearchService;

  // B2B 회원 전체 조회
  @GetMapping
  public B2BMemberPageResponse getB2BMembers(
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

    // B2B 회원 전체 조회
    Page<B2BMemberResponse> b2bMemberPage = b2bSearchService.getB2BMembers(pageRequest);

    // Page객체를 B2BMemberPageResponse로 변환하여 반환
    return new B2BMemberPageResponse(b2bMemberPage);
  }


  // 특정 상태를 가진 B2B 회원 조회 (status: ACTIVE, INACTIVE, PENDING)
  @GetMapping("/status/{status}")
  public B2BMemberPageResponse getB2BMembersByStatus(
      @PathVariable B2BMemberStatus status,
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

    // 특정 상태에 해당하는 B2B 회원 조회
    Page<B2BMemberResponse> b2bMemberPage = b2bSearchService.getB2BMembersByStatus(status,
        pageRequest);

    return new B2BMemberPageResponse(b2bMemberPage);
  }

  // 판매자 권한 변경 (ACTIVE,INACTIVE)
  @PatchMapping("/{b2bMemberId}/status")
  public void updateB2BMemberStatus(
      @PathVariable Long b2bMemberId,
      @RequestBody B2BMemberStatusRequest statusRequest) {

    if (statusRequest == null || statusRequest.getStatus() == null) {
      throw new IllegalArgumentException("잘못된 상태 값입니다.");
    }
    b2bSearchService.updateB2BMemberStatus(b2bMemberId, statusRequest.getStatus());
  }
}
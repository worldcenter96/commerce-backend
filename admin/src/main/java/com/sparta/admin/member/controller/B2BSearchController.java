package com.sparta.admin.member.controller;

import com.sparta.admin.member.dto.response.B2BMemberPageResponse;
import com.sparta.admin.member.service.B2BSearchService;
import com.sparta.common.annotation.CheckAuth;
import com.sparta.common.enums.Role;
import com.sparta.impostor.commerce.backend.domain.b2bMember.enums.B2BMemberStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class B2BSearchController {

  private final B2BSearchService b2bSearchService;

  /**
   * B2B 회원 전체 조회
   *
   * @param page    현재 페이지 (기본값 1)
   * @param size    페이지 당 사이즈 (기본값 10)
   * @param sortBy  정렬 기준 (기본값 id)
   * @param orderBy 정렬 방향 (기본값 asc)
   * @return B2B 회원 목록
   */

  @CheckAuth(role = Role.ADMIN)
  @GetMapping("/b2b-members")
  public ResponseEntity<B2BMemberPageResponse> getB2BMembers(
      @RequestParam(required = false, defaultValue = "1") int page,
      @RequestParam(required = false, defaultValue = "10") int size,
      @RequestParam(required = false, defaultValue = "id") String sortBy,
      @RequestParam(required = false, defaultValue = "asc") String orderBy
  ) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(b2bSearchService.getB2BMembers(page, size, sortBy, orderBy));
  }


  /**
   * 특정 상태를 가진 B2B 회원 조회 (status: ACTIVE, INACTIVE, PENDING)
   *
   * @param status  B2B 회원 상태 (ACTIVE, INACTIVE, PENDING)
   * @param page    현재 페이지 (기본값 1)
   * @param size    페이지 당 사이즈 (기본값 10)
   * @param sortBy  정렬 기준 (기본값 id)
   * @param orderBy 정렬 방향 (기본값 asc)
   * @return 특정 상태의 B2B 회원 목록
   */

  @CheckAuth(role = Role.ADMIN)
  @GetMapping("/b2b-members/status/{status}")
  public ResponseEntity<B2BMemberPageResponse> getB2BMembersByStatus(
      @PathVariable String status,
      @RequestParam(required = false, defaultValue = "1") int page,
      @RequestParam(required = false, defaultValue = "10") int size,
      @RequestParam(required = false, defaultValue = "id") String sortBy,
      @RequestParam(required = false, defaultValue = "asc") String orderBy
  ) {

    B2BMemberStatus b2bMemberStatus = B2BMemberStatus.valueOf(status.toUpperCase());

    return ResponseEntity.status(HttpStatus.OK)
        .body(b2bSearchService.getB2BMembersByStatus(page, size, sortBy, orderBy, b2bMemberStatus));

  }

}
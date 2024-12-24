package com.sparta.admin.member.controller;

import com.sparta.admin.member.dto.response.B2CMemberPageResponse;
import com.sparta.admin.member.service.B2CSearchService;
import com.sparta.common.annotation.CheckAuth;
import com.sparta.common.enums.Role;
import com.sparta.impostor.commerce.backend.domain.b2cMember.enums.B2CMemberStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/b2c-members")
@RequiredArgsConstructor
public class B2CSearchController {

  private final B2CSearchService b2cSearchService;

  /**
   * B2C 회원 전체 조회
   *
   * @param page    현재 페이지 (기본값 1)
   * @param size    페이지 당 사이즈 (기본값 10)
   * @param sortBy  정렬 기준 (기본값 id)
   * @param orderBy 정렬 방향 (기본값 asc)
   * @return B2C 회원 목록
   */

  @CheckAuth(role = Role.ADMIN)
  @GetMapping(produces = "application/json")
  public ResponseEntity<B2CMemberPageResponse> getB2CMembers(
          @RequestParam(required = false, defaultValue = "1") int page,
          @RequestParam(required = false, defaultValue = "10") int size,
          @RequestParam(required = false, defaultValue = "id") String sortBy,
          @RequestParam(required = false, defaultValue = "asc") String orderBy
  ) {
    return ResponseEntity.status(HttpStatus.OK)
            .body(b2cSearchService.getB2CMembers(page, size, sortBy, orderBy));
  }



  /**
   * 특정 상태를 가진 B2C 회원 조회 (status: ACTIVE, INACTIVE)
   *
   * @param status  B2C 회원 상태 (ACTIVE, INACTIVE)
   * @param page    현재 페이지 (기본값 1)
   * @param size    페이지 당 사이즈 (기본값 10)
   * @param sortBy  정렬 기준 (기본값 id)
   * @param orderBy 정렬 방향 (기본값 asc)
   * @return 특정 상태의 B2C 회원 목록
   */

  @CheckAuth(role = Role.ADMIN)
  @GetMapping(value = "/status/{status}")
  public ResponseEntity<B2CMemberPageResponse> getB2CMembersByStatus(
          @PathVariable String status,
          @RequestParam(required = false, defaultValue = "1") int page,
          @RequestParam(required = false, defaultValue = "10") int size,
          @RequestParam(required = false, defaultValue = "id") String sortBy,
          @RequestParam(required = false, defaultValue = "asc") String orderBy
  ) {

    B2CMemberStatus b2cMemberStatus = B2CMemberStatus.valueOf(status.toUpperCase());

    return ResponseEntity.status(HttpStatus.OK)
            .body(b2cSearchService.getB2CMembersByStatus(page, size, sortBy, orderBy, b2cMemberStatus));

  }
}

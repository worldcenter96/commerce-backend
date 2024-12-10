package com.sparta.admin.member.controller;

import com.sparta.admin.member.dto.response.B2CMemberPageResponse;
import com.sparta.admin.member.service.B2CSearchService;
import com.sparta.impostor.commerce.backend.domain.b2cMember.enums.B2CMemberStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/b2c-members")
@RequiredArgsConstructor
public class B2CSearchController {

  private final B2CSearchService b2cSearchService;

  // B2C 회원 전체 조회
  @GetMapping
  public B2CMemberPageResponse getB2CMembers(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String orderBy,
      @RequestParam(required = false) B2CMemberStatus status
  ) {
    Sort.Direction direction =
        orderBy.equalsIgnoreCase("desc") ? Direction.DESC : Sort.Direction.ASC;

    PageRequest pageRequest = PageRequest.of(
        page - 1,
        size,
        Sort.by(direction, sortBy));

    return b2cSearchService.getB2CMembers(status, pageRequest);
  }
}

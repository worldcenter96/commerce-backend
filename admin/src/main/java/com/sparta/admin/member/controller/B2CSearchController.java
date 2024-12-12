package com.sparta.admin.member.controller;

import com.sparta.admin.member.dto.request.B2CMemberSearchRequest;
import com.sparta.admin.member.dto.response.B2CMemberPageResponse;
import com.sparta.admin.member.service.B2CSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/b2c-members")
@RequiredArgsConstructor
public class B2CSearchController {

  private final B2CSearchService b2cSearchService;

  // B2C 회원 전체 조회
  @GetMapping
  public B2CMemberPageResponse getB2CMembers(@ModelAttribute B2CMemberSearchRequest request) {

    request.setDefaults();

    // Pageable 생성
    Sort.Direction direction =
        request.getOrderBy().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

    Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(),
        Sort.by(direction, request.getSortBy()));

    return b2cSearchService.getB2CMembers(request.getStatus(), pageable);
  }
}

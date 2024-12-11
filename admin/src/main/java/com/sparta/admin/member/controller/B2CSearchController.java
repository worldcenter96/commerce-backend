package com.sparta.admin.member.controller;

import com.sparta.admin.member.dto.request.B2CMemberSearchRequest;
import com.sparta.admin.member.dto.response.B2CMemberPageResponse;
import com.sparta.admin.member.service.B2CSearchService;
import com.sparta.impostor.commerce.backend.domain.b2cMember.enums.B2CMemberStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
  public B2CMemberPageResponse getB2CMembers(@ModelAttribute B2CMemberSearchRequest request) {

    request.setDefaults();

    // DTO 에서 값 추출
    int page = request.getPage() - 1;
    int size = request.getSize();
    String sortBy = request.getSortBy();
    String orderBy = request.getOrderBy();
    B2CMemberStatus status = request.getStatus();

    {
      Sort.Direction direction =
          orderBy.equalsIgnoreCase("desc") ? Direction.DESC : Sort.Direction.ASC;

      PageRequest pageRequest = PageRequest.of(
          page,
          size,
          Sort.by(direction, sortBy));

      return b2cSearchService.getB2CMembers(status, pageRequest);
    }
  }
}

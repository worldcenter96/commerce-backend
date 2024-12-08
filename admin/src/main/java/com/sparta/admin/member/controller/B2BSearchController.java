package com.sparta.admin.member.controller;

import com.sparta.admin.member.dto.response.B2BMemberPageResponse;
import com.sparta.admin.member.service.B2BSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class B2BSearchController {

  @Autowired
  private B2BSearchService b2BSearchService;

  @GetMapping("/api/b2b-members")
  public B2BMemberPageResponse getB2BMembers(
      @RequestParam int page,
      @RequestParam int size,
      @RequestParam String sortBy,
      @RequestParam String orderBy) {

    return b2BSearchService.getB2BMembers(
        page,
        size,
        sortBy,
        orderBy);
  }
}

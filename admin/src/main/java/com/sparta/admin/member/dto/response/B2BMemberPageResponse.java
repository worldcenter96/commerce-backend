package com.sparta.admin.member.dto.response;

import com.sparta.impostor.commerce.backend.domain.b2bMember.entity.B2BMember;
import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class B2BMemberPageResponse {

  private final List<B2BMemberResponse> contents;  // List 명 contents
  private final Integer page;                      // 현재 페이지
  private final Integer size;                      // 페이지 당 사이즈
  private final Integer totalPage;                 // 총 페이지 수


  public B2BMemberPageResponse(Page<B2BMember> b2bMemberPage, int page, int size) {
    this.contents = b2bMemberPage.map(B2BMemberResponse::from).toList();
    this.page = page;                            // 요청한 페이지
    this.size = size;                            // 페이지 당 항목 수
    this.totalPage = b2bMemberPage.getTotalPages();  // 총 페이지 수
  }
}

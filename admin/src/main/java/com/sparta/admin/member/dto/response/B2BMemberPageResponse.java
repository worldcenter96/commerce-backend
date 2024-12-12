package com.sparta.admin.member.dto.response;

import com.sparta.impostor.commerce.backend.domain.b2bMember.entity.B2BMember;
import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class B2BMemberPageResponse {

  private List<B2BMemberResponse> members;
  private Integer totalPages;
  private Long totalElements;

  public B2BMemberPageResponse(Page<B2BMember> b2bMemberPage) {
    this.members = b2bMemberPage.map(B2BMemberResponse::from).stream().toList();
    this.totalPages = b2bMemberPage.getTotalPages();
    this.totalElements = b2bMemberPage.getTotalElements();
  }
}

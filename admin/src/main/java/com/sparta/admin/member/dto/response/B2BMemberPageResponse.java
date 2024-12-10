package com.sparta.admin.member.dto.response;

import java.util.List;
import org.springframework.data.domain.Page;

public record B2BMemberPageResponse(
    List<B2BMemberResponse> members,
    int page,
    int size
) {

  public B2BMemberPageResponse(Page<B2BMemberResponse> pageResponse) {
    this(pageResponse.getContent(),
        pageResponse.getPageable().getPageNumber() + 1,
        pageResponse.getPageable().getPageSize());
  }
}

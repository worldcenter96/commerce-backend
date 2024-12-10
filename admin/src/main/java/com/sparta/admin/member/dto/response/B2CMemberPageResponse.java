package com.sparta.admin.member.dto.response;

import com.sparta.impostor.commerce.backend.domain.b2cMember.entity.B2CMember;
import java.util.List;
import org.springframework.data.domain.Page;

public record B2CMemberPageResponse(

    List<B2CMemberResponse> contents,
    int page,
    int size,
    int totalPages
) {

  public B2CMemberPageResponse(Page<B2CMember> pageResponse) {
    this(
        pageResponse.getContent().stream()
            .map(B2CMemberResponse::from)
            .toList(),
        pageResponse.getPageable().getPageNumber() + 1,
        pageResponse.getPageable().getPageSize(),
        pageResponse.getTotalPages()
    );
  }

}

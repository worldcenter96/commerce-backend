package com.sparta.admin.member.dto.response;

import com.sparta.impostor.commerce.backend.domain.b2bMember.entity.B2BMember;
import com.sparta.impostor.commerce.backend.domain.b2bMember.enums.B2BMemberStatus;
import java.time.LocalDateTime;

public record B2BApprovalResponse(Long id,
                                  String email,
                                  String name,
                                  B2BMemberStatus status,
                                  LocalDateTime createdAt,
                                  LocalDateTime modifiedAt) {

  public static B2BApprovalResponse from(B2BMember member) {
    return new B2BApprovalResponse(
        member.getId(),
        member.getEmail(),
        member.getName(),
        member.getB2BMemberStatus(),
        member.getCreatedAt(),
        member.getModifiedAt()
    );
  }
}


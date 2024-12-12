package com.sparta.admin.member.dto.response;

import com.sparta.impostor.commerce.backend.domain.b2cMember.entity.B2CMember;
import com.sparta.impostor.commerce.backend.domain.b2cMember.enums.B2CMemberStatus;
import java.time.LocalDateTime;

public record B2CStatusResponse(
    Long id,
    String email,
    String name,
    B2CMemberStatus status,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt
) {

  public static B2CStatusResponse from(B2CMember b2cMember) {
    return new B2CStatusResponse(
        b2cMember.getId(),
        b2cMember.getEmail(),
        b2cMember.getName(),
        b2cMember.getB2cMemberStatus(),
        b2cMember.getCreatedAt(),
        b2cMember.getModifiedAt()
    );
  }
}

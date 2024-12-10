package com.sparta.admin.member.dto.response;

import com.sparta.impostor.commerce.backend.domain.b2cMember.entity.B2CMember;
import com.sparta.impostor.commerce.backend.domain.b2cMember.enums.B2CMemberStatus;

public record B2CMemberResponse(
    Long id,
    String email,
    String name,
    B2CMemberStatus status
) {

  public static B2CMemberResponse from(B2CMember member) {
    return new B2CMemberResponse(
        member.getId(),
        member.getEmail(),
        member.getName(),
        member.getStatus()
    );
  }

}

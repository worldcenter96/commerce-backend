package com.sparta.admin.member.dto.response;

import com.sparta.impostor.commerce.backend.domain.b2bMember.entity.B2BMember;
import com.sparta.impostor.commerce.backend.domain.b2bMember.enums.B2BMemberStatus;

public record B2BMemberResponse(
    Long id,
    String email,
    String name,
    B2BMemberStatus b2bMemberStatus) {

  public static B2BMemberResponse from(B2BMember member) {
    return new B2BMemberResponse(
        member.getId(),
        member.getEmail(),
        member.getName(),
        member.getB2BMemberStatus()
    );
  }
}

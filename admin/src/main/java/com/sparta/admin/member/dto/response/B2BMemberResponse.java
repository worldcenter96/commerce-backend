package com.sparta.admin.member.dto.response;

import com.sparta.impostor.commerce.backend.domain.b2bMember.entity.B2BMember;

public record B2BMemberResponse(
    Long id,
    String email,
    String name,
    String activeStatus,
    String roleStatus) {

  public static B2BMemberResponse from(B2BMember member) {
    return new B2BMemberResponse(
        member.getId(),
        member.getEmail(),
        member.getName(),
        member.getActiveStatus().name(),
        member.getRoleStatus().name()
    );
  }
}

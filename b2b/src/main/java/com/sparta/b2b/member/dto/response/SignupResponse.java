package com.sparta.b2b.member.dto.response;

import com.sparta.impostor.commerce.backend.domain.b2bMember.entity.B2BMember;
import com.sparta.impostor.commerce.backend.domain.b2bMember.enums.B2BMemberStatus;

import java.time.LocalDateTime;

public record SignupResponse(
        Long id,
        String email,
        String name,
        B2BMemberStatus status,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {

    public static SignupResponse from(B2BMember member) {
        return new SignupResponse(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getB2BMemberStatus(),
                member.getCreatedAt(),
                member.getModifiedAt()
        );
    }

}

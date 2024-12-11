package com.sparta.b2c.member.dto.response;

import com.sparta.impostor.commerce.backend.domain.b2cMember.entity.B2CMember;
import com.sparta.impostor.commerce.backend.domain.b2cMember.enums.B2CMemberStatus;

import java.time.LocalDateTime;

public record SignupResponse(
        Long id,
        String email,
        String name,
        B2CMemberStatus status,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static SignupResponse from(B2CMember member) {
        return new SignupResponse(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getStatus(),
                member.getCreatedAt(),
                member.getModifiedAt()
        );
    }
}

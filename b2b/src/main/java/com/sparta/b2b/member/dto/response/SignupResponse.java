package com.sparta.b2b.member.dto.response;

import com.sparta.impostor.commerce.backend.domain.b2bMember.entity.B2BMember;
import com.sparta.impostor.commerce.backend.domain.b2bMember.enums.ApproveStatus;

import java.time.LocalDateTime;

public record SignupResponse(
        Long id,
        String email,
        String name,
        ApproveStatus status,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {

    public static SignupResponse from(B2BMember member) {
        return new SignupResponse(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getApproveStatus(),
                member.getCreatedAt(),
                member.getModifiedAt()
        );
    }

}

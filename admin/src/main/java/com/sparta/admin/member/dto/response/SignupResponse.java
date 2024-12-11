package com.sparta.admin.member.dto.response;

import com.sparta.impostor.commerce.backend.domain.adminMember.entity.AdminMember;

import java.time.LocalDateTime;

public record SignupResponse(
        Long id,
        String email,
        String name,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static SignupResponse from(AdminMember member) {
        return new SignupResponse(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getCreatedAt(),
                member.getModifiedAt()
        );
    }
}

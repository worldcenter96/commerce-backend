package com.sparta.admin.member.dto.request;

import com.sparta.impostor.commerce.backend.domain.b2bMember.enums.B2BMemberStatus;

public record B2BMemberStatusRequest(
    B2BMemberStatus status
) {
}

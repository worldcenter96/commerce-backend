package com.sparta.admin.member.dto.request;

import com.sparta.impostor.commerce.backend.domain.b2cMember.enums.B2CMemberStatus;

public record B2CStatusRequest(
    B2CMemberStatus status
) {

}

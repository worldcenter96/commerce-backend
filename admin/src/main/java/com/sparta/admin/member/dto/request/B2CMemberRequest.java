package com.sparta.admin.member.dto.request;

import java.time.LocalDateTime;

public record B2CMemberRequest(
    Long memberId,
    String email,
    String name,
    LocalDateTime requestedAt) {

}

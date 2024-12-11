package com.sparta.impostor.commerce.backend.domain.b2cMember.enums;

public enum B2CMemberStatus {
    PENDING, // 판매자 권한 신청 대기, 요청 확인을 위한 상태 추가
    ACTIVE, // 판매자 권한 승인
    INACTIVE // 판매자 권한 거절
}

package com.sparta.common.dto;

// Redis에 key-value 형태로 저장될 때 value에 저장될 값을 DTO로 생성
public record MemberSession(
        Long memberId
) {
}

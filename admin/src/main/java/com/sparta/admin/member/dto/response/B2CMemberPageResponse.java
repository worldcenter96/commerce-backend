package com.sparta.admin.member.dto.response;

import com.sparta.impostor.commerce.backend.domain.b2cMember.entity.B2CMember;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class B2CMemberPageResponse {
    private final List<B2CMemberResponse> members;
    private final Integer totalPages;
    private final Long totalElements;


    public B2CMemberPageResponse(Page<B2CMember> b2cMemberPage) {
        this.members = b2cMemberPage.map(B2CMemberResponse::from).toList();
        this.totalPages = b2cMemberPage.getTotalPages();
        this.totalElements = b2cMemberPage.getTotalElements();
    }
}

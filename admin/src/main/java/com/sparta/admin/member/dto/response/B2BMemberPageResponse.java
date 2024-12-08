package com.sparta.admin.member.dto.response;

import java.util.List;

public record B2BMemberPageResponse(
    List<B2BMemberResponse> members,
    int page,
    int size,
    String sortBy,
    String orderBy,
    int totalPages
) {}

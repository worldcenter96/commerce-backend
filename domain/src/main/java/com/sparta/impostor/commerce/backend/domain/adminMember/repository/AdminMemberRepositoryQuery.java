package com.sparta.impostor.commerce.backend.domain.adminMember.repository;

import com.sparta.impostor.commerce.backend.domain.adminMember.entity.AdminMember;

import java.util.Optional;

public interface AdminMemberRepositoryQuery {

    Optional<AdminMember> findByEmail(String email);
}

package com.sparta.impostor.commerce.backend.domain.b2bMember.repository;

import com.sparta.impostor.commerce.backend.domain.b2bMember.entity.B2BMember;

import java.util.Optional;

public interface B2BMemberRepositoryQuery {

    Optional<B2BMember> findByEmail(String email);
}

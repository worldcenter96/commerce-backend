package com.sparta.impostor.commerce.backend.domain.b2cMember.repository;

import com.sparta.impostor.commerce.backend.domain.b2cMember.entity.B2CMember;

import java.util.Optional;

public interface B2CMemberRepositoryQuery {

    Optional<B2CMember> findByEmail(String email);
}

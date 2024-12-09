package com.sparta.impostor.commerce.backend.domain.b2bMember.repository;

import com.sparta.impostor.commerce.backend.domain.b2bMember.entity.B2BMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface B2BMemberRepository extends JpaRepository<B2BMember, Long>, B2BMemberRepositoryQuery {
}

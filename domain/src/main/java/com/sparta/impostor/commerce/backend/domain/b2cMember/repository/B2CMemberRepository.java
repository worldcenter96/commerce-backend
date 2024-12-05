package com.sparta.impostor.commerce.backend.domain.b2cMember.repository;

import com.sparta.impostor.commerce.backend.domain.b2cMember.entity.B2CMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface B2CMemberRepository extends JpaRepository<B2CMember, Long> {
}

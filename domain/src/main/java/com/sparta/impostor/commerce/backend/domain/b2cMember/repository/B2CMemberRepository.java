package com.sparta.impostor.commerce.backend.domain.b2cMember.repository;

import com.sparta.impostor.commerce.backend.domain.b2cMember.entity.B2CMember;
import com.sparta.impostor.commerce.backend.domain.b2cMember.enums.B2CMemberStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface B2CMemberRepository extends JpaRepository<B2CMember, Long>, B2CMemberRepositoryQuery {

  // 모든 B2C 회원 조회 (전체 조회)
  Page<B2CMember> findAll(Pageable pageable);

  // 상태에 맞는 B2C 회원 조회
  Page<B2CMember> findByB2CMemberStatus(B2CMemberStatus status, Pageable pageable);

}

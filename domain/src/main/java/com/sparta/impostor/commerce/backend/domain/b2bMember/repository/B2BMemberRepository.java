package com.sparta.impostor.commerce.backend.domain.b2bMember.repository;

import com.sparta.impostor.commerce.backend.domain.b2bMember.entity.B2BMember;
import com.sparta.impostor.commerce.backend.domain.b2bMember.enums.B2BMemberStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface B2BMemberRepository extends JpaRepository<B2BMember, Long>, B2BMemberRepositoryQuery {

  // B2B 회원 상태로 회원을 조회 (페이지네이션 적용)
  Page<B2BMember> findByB2BMemberStatus(B2BMemberStatus status, PageRequest pageRequest);
}

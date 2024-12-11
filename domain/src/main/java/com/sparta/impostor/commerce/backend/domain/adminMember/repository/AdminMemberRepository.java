package com.sparta.impostor.commerce.backend.domain.adminMember.repository;

import com.sparta.impostor.commerce.backend.domain.adminMember.entity.AdminMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminMemberRepository extends JpaRepository<AdminMember, Long>, AdminMemberRepositoryQuery {
}

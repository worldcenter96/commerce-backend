package com.sparta.impostor.commerce.backend.domain.adminMember.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.impostor.commerce.backend.domain.adminMember.entity.AdminMember;
import com.sparta.impostor.commerce.backend.domain.adminMember.entity.QAdminMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminMemberRepositoryQueryImpl implements AdminMemberRepositoryQuery {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QAdminMember adminMember = QAdminMember.adminMember;

    @Override
    public Optional<AdminMember> findByEmail(String email) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(adminMember)
                .where(adminMember.email.eq(email))
                .fetchOne());
    }
}

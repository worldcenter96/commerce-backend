package com.sparta.impostor.commerce.backend.domain.b2cMember.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.impostor.commerce.backend.domain.b2cMember.entity.B2CMember;
import com.sparta.impostor.commerce.backend.domain.b2cMember.entity.QB2CMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class B2CMemberRepositoryQueryImpl implements B2CMemberRepositoryQuery {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QB2CMember b2cMember = QB2CMember.b2CMember;

    @Override
    public Optional<B2CMember> findByEmail(String email) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(b2cMember)
                .where(b2cMember.email.eq(email))
                .fetchOne());
    }
}

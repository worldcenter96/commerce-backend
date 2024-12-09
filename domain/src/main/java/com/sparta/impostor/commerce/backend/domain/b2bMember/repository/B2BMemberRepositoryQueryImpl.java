package com.sparta.impostor.commerce.backend.domain.b2bMember.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.impostor.commerce.backend.domain.b2bMember.entity.B2BMember;
import com.sparta.impostor.commerce.backend.domain.b2bMember.entity.QB2BMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class B2BMemberRepositoryQueryImpl extends QuerydslRepositorySupport implements B2BMemberRepositoryQuery {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QB2BMember b2bMember = QB2BMember.b2BMember;

    public B2BMemberRepositoryQueryImpl() {
        super(B2BMember.class);
    }

    @Override
    public Optional<B2BMember> findByEmail(String email) {

        return Optional.ofNullable(jpaQueryFactory.selectFrom(b2bMember)
                .where(b2bMember.email.eq(email))
                .fetchOne());
    }
}

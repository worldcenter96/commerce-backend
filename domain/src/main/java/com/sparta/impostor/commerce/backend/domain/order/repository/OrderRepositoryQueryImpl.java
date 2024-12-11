package com.sparta.impostor.commerce.backend.domain.order.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.impostor.commerce.backend.domain.order.entity.Orders;
import com.sparta.impostor.commerce.backend.domain.order.entity.QOrders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OrderRepositoryQueryImpl extends QuerydslRepositorySupport implements OrderRepositoryQuery {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QOrders orders = QOrders.orders;

    public OrderRepositoryQueryImpl() {
        super(Orders.class);
    }

    @Override
    public Page<Orders> findAllByMemberId(Pageable pageable, Long memberId) {
        JPAQuery<Orders> query = jpaQueryFactory.selectFrom(orders)
                .where(orders.b2BMemberId.eq(memberId));

        List<Orders> ordersList = this.getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(ordersList, pageable, query.fetchCount());
    }

    @Override
    public Optional<Orders> findByIdANDMemberId(Long id, Long memberId) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(orders)
                .where(orders.id.eq(id).and(orders.b2BMemberId.eq(memberId)))
                .fetchOne());

    }
}

package com.sparta.impostor.commerce.backend.domain.order.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.impostor.commerce.backend.domain.order.entity.Order;
import com.sparta.impostor.commerce.backend.domain.order.entity.QOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

@Component
public class OrderRepositoryCustomImpl extends QuerydslRepositorySupport implements OrderRepositoryCustom {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QOrder order = QOrder.order;

    public OrderRepositoryCustomImpl() {
        super(Order.class);
    }

}

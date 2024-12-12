package com.sparta.impostor.commerce.backend.domain.order.repository;

import com.sparta.impostor.commerce.backend.domain.order.entity.Orders;
import com.sparta.impostor.commerce.backend.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long>, OrderRepositoryQuery {
    Page<Orders> findAllByMemberId(Long memberId, Pageable pageable);
}

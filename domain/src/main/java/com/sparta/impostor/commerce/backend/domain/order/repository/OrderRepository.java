package com.sparta.impostor.commerce.backend.domain.order.repository;

import com.sparta.impostor.commerce.backend.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom{
}

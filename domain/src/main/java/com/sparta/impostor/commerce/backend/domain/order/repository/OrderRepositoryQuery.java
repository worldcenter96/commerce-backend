package com.sparta.impostor.commerce.backend.domain.order.repository;

import com.sparta.impostor.commerce.backend.domain.order.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderRepositoryQuery {

    Page<Orders> findAllByMemberId(Pageable pageable, Long memberId);

    Optional<Orders> findByIdANDMemberId(Long id, Long memberId);
}

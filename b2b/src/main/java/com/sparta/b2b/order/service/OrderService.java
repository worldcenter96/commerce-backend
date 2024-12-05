package com.sparta.b2b.order.service;

import com.sparta.b2b.order.dto.response.OrderPageResponse;
import com.sparta.impostor.commerce.backend.domain.order.entity.Order;
import com.sparta.impostor.commerce.backend.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderPageResponse retrieveOrderList(int page, int size, String sortBy, String orderBy) {

        Pageable pageable = null;

        if (orderBy.equals("DESC")) {
            pageable = PageRequest.of(page-1, size, Sort.by(Sort.Direction.DESC, sortBy));
        } else if (orderBy.equals("ASC")) {
            pageable = PageRequest.of(page-1, size, Sort.by(Sort.Direction.ASC, sortBy));
        }

        Page<Order> orders = orderRepository.findAll(pageable);

        return new OrderPageResponse(orders);
    }
}

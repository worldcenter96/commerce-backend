package com.sparta.b2b.order.service;

import com.sparta.b2b.order.dto.request.DeliveryStatusRequest;
import com.sparta.b2b.order.dto.response.OrderPageResponse;
import com.sparta.b2b.order.dto.response.OrderResponse;
import com.sparta.impostor.commerce.backend.domain.order.entity.Orders;
import com.sparta.impostor.commerce.backend.domain.order.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderPageResponse retrieveOrderList(int page, int size, String sortBy, String orderBy, Long memberId) {

        Sort.Direction direction =
                orderBy.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page-1, size, Sort.by(direction, sortBy));

        Page<Orders> orders = orderRepository.findAllByMemberId(pageable, memberId);
        if (orders == null) {
            throw new EntityNotFoundException("조회된 주문 내역이 없습니다.");
        }

        return new OrderPageResponse(orders);
    }

    @Transactional
    public OrderResponse updateDeliveryStatus(Long id, DeliveryStatusRequest request, Long memberId) {

        String trackingNumber = request.trackingNumber();

        Orders order = orderRepository.findByIdANDMemberId(id, memberId).orElseThrow(() ->
                new EntityNotFoundException("조회된 주문 내역이 없습니다. 본인의 상품인지 다시 한번 확인해주세요")
        );

        Orders updatedOrders = order.update(trackingNumber);

        return OrderResponse.from(updatedOrders);

    }
}

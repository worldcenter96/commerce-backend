package com.sparta.b2c.order.dto.response;

import com.sparta.impostor.commerce.backend.domain.order.entity.Orders;
import org.springframework.data.domain.Page;

import java.util.List;

public record PageOrderResponse(
        List<OrderResponse> orders,
        int page,
        int size,
        int totalPage
) {
    public static PageOrderResponse from(Page<Orders> orderPage) {
        return new PageOrderResponse(
                orderPage.getContent().stream().map(OrderResponse::from).toList(),
                orderPage.getNumber() + 1,
                orderPage.getSize(),
                orderPage.getTotalPages()
        );
    }
}

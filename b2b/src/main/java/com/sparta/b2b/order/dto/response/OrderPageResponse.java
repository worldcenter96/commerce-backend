package com.sparta.b2b.order.dto.response;

import com.sparta.impostor.commerce.backend.domain.order.entity.Order;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class OrderPageResponse {

    private List<OrderResponse> orderResponseList;
    private Integer totalPages;
    private Long totalElements;


    public OrderPageResponse(Page<Order> orders) {
        this.orderResponseList = orders.map(OrderResponse::from).stream().toList();
        this.totalPages = orders.getTotalPages();
        this.totalElements = orders.getTotalElements();
    }
}

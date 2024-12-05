package com.sparta.b2b.order.dto.response;

import com.sparta.impostor.commerce.backend.domain.order.entity.Order;
import com.sparta.impostor.commerce.backend.domain.order.enums.DeliveryStatus;
import com.sparta.impostor.commerce.backend.domain.order.enums.OrderStatus;

import java.time.LocalDateTime;

public record OrderResponse(
        String name,
        String trackingNumber,
        OrderStatus orderStatus,
        DeliveryStatus deliveryStatus,
        Long totalPrice,
        Long quantity,
        String memberName,
        String productName,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {

    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.getName(),
                order.getTrackingNumber(),
                order.getOrderStatus(),
                order.getDeliveryStatus(),
                order.getTotalPrice(),
                order.getQuantity(),
                order.getMember().getName(),
                order.getProduct().getName(),
                order.getCreatedAt(),
                order.getModifiedAt()
        );
    }
}

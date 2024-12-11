package com.sparta.b2c.order.dto.response;

import com.sparta.impostor.commerce.backend.domain.order.entity.Order;
import com.sparta.impostor.commerce.backend.domain.order.enums.DeliveryStatus;
import com.sparta.impostor.commerce.backend.domain.order.enums.OrderStatus;

import java.time.LocalDateTime;

public record OrderResponse(
        Long id,
        String name,
        int quantity,
        Long totalPrice,
        OrderStatus status,
        DeliveryStatus deliveryStatus,
        String trackingNumber,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {

    public static OrderResponse from(Order order) {
        return new OrderResponse(
          order.getId(),
          order.getName(),
          order.getQuantity(),
          order.getTotalPrice(),
          order.getOrderStatus(),
          order.getDeliveryStatus(),
          order.getTrackingNumber(),
          order.getCreatedAt(),
          order.getModifiedAt()
        );
    }
}

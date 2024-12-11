package com.sparta.b2b.order.dto.response;

import com.sparta.impostor.commerce.backend.domain.order.entity.Orders;
import com.sparta.impostor.commerce.backend.domain.order.enums.DeliveryStatus;
import com.sparta.impostor.commerce.backend.domain.order.enums.OrderStatus;

import java.time.LocalDateTime;

public record OrderResponse(
        Long id,
        String name,
        String trackingNumber,
        OrderStatus orderStatus,
        DeliveryStatus deliveryStatus,
        Long totalPrice,
        Integer quantity,
        String b2cMemberName,
        Long b2bMemberId,
        String productName,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {

    public static OrderResponse from(Orders orders) {
        return new OrderResponse(
                orders.getId(),
                orders.getName(),
                orders.getTrackingNumber(),
                orders.getOrderStatus(),
                orders.getDeliveryStatus(),
                orders.getTotalPrice(),
                orders.getQuantity(),
                orders.getB2CMember().getName(),
                orders.getB2BMemberId(),
                orders.getProduct().getName(),
                orders.getCreatedAt(),
                orders.getModifiedAt()
        );
    }


}

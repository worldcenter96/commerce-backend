package com.sparta.impostor.commerce.backend.domain.order.entity;

import com.sparta.impostor.commerce.backend.common.baseentity.Timestamped;
import com.sparta.impostor.commerce.backend.domain.b2cMember.entity.B2CMember;
import com.sparta.impostor.commerce.backend.domain.order.enums.DeliveryStatus;
import com.sparta.impostor.commerce.backend.domain.order.enums.OrderStatus;
import com.sparta.impostor.commerce.backend.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "orders")
public class Order extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 50)
    private String trackingNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @Column(nullable = false)
    private Long totalPrice;

    @Column(nullable = false)
    private Long quantity;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private B2CMember member;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


    public Order update(String trackingNumber) {
        this.trackingNumber = trackingNumber;
        this.deliveryStatus = DeliveryStatus.IN_TRANSIT;
        return this;
    }
}

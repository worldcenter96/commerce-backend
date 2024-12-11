package com.sparta.impostor.commerce.backend.domain.order.entity;

import com.sparta.impostor.commerce.backend.common.baseentity.Timestamped;
import com.sparta.impostor.commerce.backend.domain.b2cMember.entity.B2CMember;
import com.sparta.impostor.commerce.backend.domain.order.enums.DeliveryStatus;
import com.sparta.impostor.commerce.backend.domain.order.enums.OrderStatus;
import com.sparta.impostor.commerce.backend.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Orders extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private Long totalPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @Column(length = 50, nullable = false)
    private String trackingNumber;

    @ManyToOne
    @JoinColumn(name = "b2cmember_id")
    private B2CMember b2CMember;


    @Column(nullable = false)
    private Long b2BMemberId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


    public Orders update(String trackingNumber) {
        this.trackingNumber = trackingNumber;
        this.deliveryStatus = DeliveryStatus.IN_TRANSIT;
        return this;
    }

    private Orders(String name, int quantity, Long totalPrice, OrderStatus orderStatus, DeliveryStatus deliveryStatus, String trackingNumber, Product product, B2CMember b2CMember, Long b2BMemberId) {
        this.name = name;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.deliveryStatus = deliveryStatus;
        this.trackingNumber = trackingNumber;
        this.product = product;
        this.b2CMember = b2CMember;
        this.b2BMemberId = b2BMemberId;
    }

    public static Orders create(String name, int quantity, Long totalPrice, OrderStatus orderStatus, DeliveryStatus deliveryStatus, String trackingNumber, Product product, B2CMember b2CMember, Long b2BMemberId) {
        return new Orders(name, quantity, totalPrice, orderStatus, deliveryStatus, trackingNumber, product, b2CMember, b2BMemberId);
    }

}

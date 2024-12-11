package com.sparta.b2c.order.service;

import com.sparta.b2c.order.dto.request.OrderRequest;
import com.sparta.b2c.order.dto.response.OrderResponse;
import com.sparta.impostor.commerce.backend.domain.b2cMember.entity.B2CMember;
import com.sparta.impostor.commerce.backend.domain.b2cMember.repository.B2CMemberRepository;
import com.sparta.impostor.commerce.backend.domain.order.entity.Orders;
import com.sparta.impostor.commerce.backend.domain.order.enums.DeliveryStatus;
import com.sparta.impostor.commerce.backend.domain.order.enums.OrderStatus;
import com.sparta.impostor.commerce.backend.domain.order.repository.OrderRepository;
import com.sparta.impostor.commerce.backend.domain.product.entity.Product;
import com.sparta.impostor.commerce.backend.domain.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final B2CMemberRepository b2cMemberRepository;

    @Transactional
    public OrderResponse createOrder(Long b2cMemberId, OrderRequest orderRequest) {

        B2CMember b2CMember = b2cMemberRepository.findById(b2cMemberId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        Product product = productRepository.findById(orderRequest.productId())
                .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다."));

        // 상품의 수량이 주문 수량의 이상인지 검증
        int quantity = orderRequest.quantity();
        if (quantity > product.getStockQuantity()) {
            throw new IllegalArgumentException("재고 수량이 부족합니다.");
        }

        Long totalPrice = Long.valueOf(product.getPrice() * quantity);

        Orders order = Orders.create(
                product.getName(),
                quantity,
                totalPrice,
                OrderStatus.CONFIRMED,
                DeliveryStatus.NOT_SHIPPED,
                "",   // trackingNumber 초기 값
                product,
                b2CMember,
                product.getId()
        );

        orderRepository.save(order);
        return OrderResponse.from(order);

    }
}

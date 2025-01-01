package com.sparta.b2c.order.service;

import com.sparta.b2c.order.dto.request.OrderRequest;
import com.sparta.b2c.order.dto.request.OrderStatusRequest;
import com.sparta.b2c.order.dto.response.OrderResponse;
import com.sparta.b2c.order.dto.response.PageOrderResponse;
import com.sparta.common.dto.MemberSession;
import com.sparta.impostor.commerce.backend.common.exception.ForbiddenAccessException;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final B2CMemberRepository b2cMemberRepository;

    @Transactional
    public OrderResponse createOrder(Long memberId, OrderRequest orderRequest) {

        B2CMember b2CMember = b2cMemberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        Product product = productRepository.findById(orderRequest.productId())
                .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다."));

        // 상품의 수량이 주문 수량의 이상인지 검증
        int quantity = orderRequest.quantity();
        if (quantity > product.getStockQuantity() && product.getStockQuantity() <= 0) {
            throw new IllegalArgumentException("재고 수량이 부족합니다.");
        }

        int updatedQuantity = product.getStockQuantity() - quantity;
        product.updateQuantity(updatedQuantity);

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

    public PageOrderResponse searchOrderList(int page, int size, String orderBy,String sortBy, Long memberId) {
        Sort.Direction direction = "ASC".equalsIgnoreCase(orderBy) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page - 1, size, sort);


        Page<Orders> orderPage = orderRepository.findAllByB2CMemberId(memberId, pageable);

        return PageOrderResponse.from(orderPage);
    }

    public OrderResponse searchOrder(long id, Long memberId) {
        Orders orders = orderRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("해당 주문을 찾지 못했습니다."));

        if(!orders.getB2CMember().getId().equals(memberId)) {
            throw new ForbiddenAccessException("본인의 주문만 열람할 수 있습니다.");
        }

        return OrderResponse.from(orders);
    }

    @Transactional
    public OrderResponse refundOrder(long id, OrderStatusRequest request, Long memberId) {

        String orderStatus = request.orderStatus();

        Orders orders = orderRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("해당 주문을 찾지 못했습니다."));

        if(!orders.getB2CMember().getId().equals(memberId)) {
            throw new ForbiddenAccessException("본인의 주문만 환불 요청 할 수 있습니다.");
        }

        if (!OrderStatus.REFUND_REQUEST.name().equals(orderStatus)) {
            throw new IllegalArgumentException("REFUND_REQUEST가 아닌 다른 값으로 변경할 권한이 없습니다.");
        }

        orders.updateOrderStatus(OrderStatus.REFUND_REQUEST);
        return OrderResponse.from(orders);
    }

}

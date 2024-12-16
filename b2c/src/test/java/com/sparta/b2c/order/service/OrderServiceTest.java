package com.sparta.b2c.order.service;

import com.sparta.b2c.order.dto.request.OrderRequest;
import com.sparta.b2c.order.dto.request.OrderStatusRequest;
import com.sparta.b2c.order.dto.response.OrderResponse;
import com.sparta.b2c.order.dto.response.PageOrderResponse;
import com.sparta.impostor.commerce.backend.domain.b2cMember.entity.B2CMember;
import com.sparta.impostor.commerce.backend.domain.b2cMember.repository.B2CMemberRepository;
import com.sparta.impostor.commerce.backend.domain.order.entity.Orders;
import com.sparta.impostor.commerce.backend.domain.order.enums.OrderStatus;
import com.sparta.impostor.commerce.backend.domain.order.repository.OrderRepository;
import com.sparta.impostor.commerce.backend.domain.product.entity.Product;
import com.sparta.impostor.commerce.backend.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private B2CMemberRepository b2cMemberRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("주문 등록 성공 테스트")
    void createOrder_success() {
        Long memberId = 1L;
        OrderRequest orderRequest = new OrderRequest(1L, 2);

        B2CMember b2cMember = mock(B2CMember.class);
        Product product = mock(Product.class);

        when(b2cMemberRepository.findById(memberId)).thenReturn(Optional.of(b2cMember));
        when(productRepository.findById(orderRequest.productId())).thenReturn(Optional.of(product));
        when(product.getStockQuantity()).thenReturn(10);
        when(product.getPrice()).thenReturn(100);

        Orders savedOrder = mock(Orders.class);
        when(orderRepository.save(any(Orders.class))).thenReturn(savedOrder);

        OrderResponse response = orderService.createOrder(memberId, orderRequest);

        assertNotNull(response);
        verify(orderRepository, times(1)).save(any(Orders.class));
    }

    @Test
    @DisplayName("주문 시 재고 부족 예외처리 테스트")
    void createOrder_insufficientStock() {
        Long memberId = 1L;
        OrderRequest orderRequest = new OrderRequest(1L, 20);

        B2CMember b2cMember = mock(B2CMember.class);
        Product product = mock(Product.class);

        when(b2cMemberRepository.findById(memberId)).thenReturn(Optional.of(b2cMember));
        when(productRepository.findById(orderRequest.productId())).thenReturn(Optional.of(product));
        when(product.getStockQuantity()).thenReturn(10);

        assertThrows(IllegalArgumentException.class, () -> orderService.createOrder(memberId, orderRequest));
    }

    @Test
    @DisplayName("주문 목록 조회 성공 테스트")
    void searchOrderList_success() {
        Long memberId = 1L;
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("modifiedAt").descending());
        Page<Orders> orderPage = new PageImpl<>(Collections.emptyList(), pageRequest, 0);

        when(orderRepository.findAllByB2CMemberId(memberId, pageRequest)).thenReturn(orderPage);

        PageOrderResponse response = orderService.searchOrderList(1, 10, "DESC", "modifiedAt", memberId);

        assertNotNull(response);
        assertEquals(0, response.contents().size());
    }

    @Test
    @DisplayName("다른 사용자의 주문 접근에 대한 예외 처리 테스트")
    void searchOrder_invalidAccess() {
        Long memberId = 1L;
        Long orderId = 1L;
        Orders order = mock(Orders.class);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(order.getB2CMember()).thenReturn(mock(B2CMember.class));
        when(order.getB2CMember().getId()).thenReturn(2L);

        assertThrows(RuntimeException.class, () -> orderService.searchOrder(orderId, memberId));
    }

    @Test
    @DisplayName("환불 요청 처리 성공 테스트")
    void refundOrder_success() {
        Long memberId = 1L;
        Long orderId = 1L;
        OrderStatusRequest request = new OrderStatusRequest(OrderStatus.REFUND_REQUEST.name());

        Orders order = mock(Orders.class);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(order.getB2CMember()).thenReturn(mock(B2CMember.class));
        when(order.getB2CMember().getId()).thenReturn(memberId);

        OrderResponse response = orderService.refundOrder(orderId, request, memberId);

        assertNotNull(response);
        verify(order, times(1)).updateOrderStatus(OrderStatus.REFUND_REQUEST);
    }
}

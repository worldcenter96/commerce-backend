package com.sparta.b2c.order.controller;

import com.sparta.b2c.order.dto.request.OrderRequest;
import com.sparta.b2c.order.dto.response.OrderResponse;
import com.sparta.b2c.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping()
    public ResponseEntity<OrderResponse>createOrder(@Valid @RequestBody OrderRequest orderRequest) {

        // b2c 로그인 구현 후 로그인 값 변경 예정
        OrderResponse orderResponse = orderService.createOrder(1L, orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
    }

}

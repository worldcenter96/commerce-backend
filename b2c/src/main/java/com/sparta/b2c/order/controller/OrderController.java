package com.sparta.b2c.order.controller;

import com.sparta.b2c.order.dto.request.OrderRequest;
import com.sparta.b2c.order.dto.response.OrderResponse;
import com.sparta.b2c.order.dto.response.PageOrderResponse;
import com.sparta.b2c.order.service.OrderService;
import com.sparta.common.annotation.CheckAuth;
import com.sparta.common.annotation.LoginMember;
import com.sparta.common.dto.MemberSession;
import com.sparta.common.enums.Role;
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

    @CheckAuth(role = Role.B2C)
    @PostMapping()
    public ResponseEntity<OrderResponse>createOrder(@Valid @RequestBody OrderRequest orderRequest,
                                                    @LoginMember(role = Role.B2B) MemberSession memberSession) {

        // b2c 로그인 구현 후 로그인 값 변경 예정
        OrderResponse orderResponse = orderService.createOrder(memberSession.memberId(), orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
    }

    @CheckAuth(role = Role.B2C)
    @GetMapping()
    public ResponseEntity<PageOrderResponse>searchOrderList(@RequestParam(defaultValue = "1") int page,
                                                            @RequestParam(defaultValue = "10") int size,
                                                            @RequestParam(required = false, defaultValue = "modifiedAt") String sortBy,
                                                            @RequestParam(required = false, defaultValue = "DESC") String orderBy,
                                                            @LoginMember(role = Role.B2C) MemberSession memberSession) {

        return ResponseEntity.status(HttpStatus.OK).body(orderService.searchOrderList(page, size, sortBy, orderBy, memberSession.memberId()));

    }
}

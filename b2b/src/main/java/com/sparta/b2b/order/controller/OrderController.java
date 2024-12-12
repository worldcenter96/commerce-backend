package com.sparta.b2b.order.controller;

import com.sparta.b2b.order.dto.request.DeliveryStatusRequest;
import com.sparta.b2b.order.dto.response.OrderPageResponse;
import com.sparta.b2b.order.dto.response.OrderResponse;
import com.sparta.b2b.order.service.OrderService;
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
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @CheckAuth(role = Role.B2B)
    @GetMapping()
    public ResponseEntity<OrderPageResponse> retrieveOrderList(@RequestParam(defaultValue = "1") int page,
                                                               @RequestParam(defaultValue = "10") int size,
                                                               @RequestParam(required = false, defaultValue = "modifiedAt") String sortBy,
                                                               @RequestParam(required = false, defaultValue = "DESC") String orderBy,
                                                               @LoginMember(role = Role.B2B) MemberSession memberSession) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.retrieveOrderList(page, size, sortBy, orderBy, memberSession.memberId()));
    }

    @CheckAuth(role = Role.B2B)
    @PatchMapping("/{id}/delivery-status")
    public ResponseEntity<OrderResponse> updateDeliveryStatus(@PathVariable Long id,
                                                              @RequestBody @Valid DeliveryStatusRequest request,
                                                              @LoginMember(role = Role.B2B) MemberSession memberSession) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.updateDeliveryStatus(id, request, memberSession.memberId()));
    }

}

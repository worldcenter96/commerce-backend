package com.sparta.b2b.order.controller;

import com.sparta.b2b.order.dto.request.DeliveryStatusRequest;
import com.sparta.b2b.order.dto.response.OrderPageResponse;
import com.sparta.b2b.order.dto.response.OrderResponse;
import com.sparta.b2b.order.service.OrderService;
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

    @GetMapping()
    public ResponseEntity<OrderPageResponse> retrieveOrderList(@RequestParam(defaultValue = "1") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam(required = false, defaultValue = "modifiedAt") String sortBy,
                                                           @RequestParam(required = false, defaultValue = "DESC") String orderBy) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.retrieveOrderList(page, size, sortBy, orderBy));
    }

    @PatchMapping("/{id}/deliverystatus")
    public ResponseEntity<OrderResponse> updateDeliveryStatus(@PathVariable Long id,
                                                              @RequestBody @Valid DeliveryStatusRequest request) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.updateDeliveryStatus(id, request));
    }

}

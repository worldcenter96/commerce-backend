package com.sparta.b2b.order.controller;

import com.sparta.b2b.order.dto.response.OrderPageResponse;
import com.sparta.b2b.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}

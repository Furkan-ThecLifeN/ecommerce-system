package com.ecommerce.order.controller;

import com.ecommerce.order.model.Order;
import com.ecommerce.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public Order placeOrder(@RequestBody Order order, 
                            @RequestHeader(value = "X-Request-Id", required = false) String requestId) {
        // 5.3.1 Correlation ID Loglaması
        log.info("Received order request. Correlation ID: {}", requestId);
        return orderService.placeOrder(order);
    }
}
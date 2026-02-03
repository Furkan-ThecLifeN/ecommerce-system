package com.ecommerce.order.service;

import com.ecommerce.order.model.Order;
import com.ecommerce.order.repository.OrderRepository;
import com.ecommerce.common.event.OrderCreatedEvent;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    @CircuitBreaker(name = "orderServiceCB", fallbackMethod = "placeOrderFallback") 
    public Order placeOrder(Order order) {
        order.setStatus("CREATED");
        String paymentId = "PAY-" + UUID.randomUUID().toString();

        Order savedOrder = orderRepository.save(order);

        // 5.1.2 Versioning uygulanmış event gönderimi
        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .version(1)
                .orderId(savedOrder.getId())
                .userId(savedOrder.getUserId())
                .totalPrice(savedOrder.getTotalPrice())
                .paymentId(paymentId)
                .build();

        kafkaTemplate.send("order.created", event);
        log.info("Order created and event sent to Kafka. Order ID: {}", savedOrder.getId());

        return savedOrder;
    }

    // 5.2.3 Fallback Metodu
    public Order placeOrderFallback(Order order, Exception e) {
        log.error("Circuit Breaker triggered! Fallback active for Order. Error: {}", e.getMessage());
        order.setStatus("FAILED_SYSTEM_BUSY");
        // Burada veritabanına kaydedebilir veya kullanıcıya özel bir hata nesnesi dönebilirsin
        return order; 
    }
}
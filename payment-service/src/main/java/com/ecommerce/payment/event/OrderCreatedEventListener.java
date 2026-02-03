package com.ecommerce.payment.event;

import com.ecommerce.common.event.OrderCreatedEvent;
import com.ecommerce.payment.entity.Payment;
import com.ecommerce.payment.entity.PaymentStatus;
import com.ecommerce.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCreatedEventListener {

    private final PaymentService paymentService;

    @KafkaListener(topics = "order.created", groupId = "payment-group")
    public void consume(OrderCreatedEvent event) {
        log.info("Order Created Event received for order: {}", event.getOrderId());

        Payment payment = Payment.builder()
                .paymentId(event.getPaymentId())
                .orderId(event.getOrderId())
                .amount(event.getTotalPrice())
                .status(PaymentStatus.SUCCESS) // Şimdilik simülasyon gereği direkt SUCCESS
                .build();

        paymentService.processPayment(payment);
    }
}
package com.ecommerce.payment.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentCompletedEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendPaymentCompletedEvent(UUID orderId, String paymentId, String status) {
        PaymentCompletedEvent event = new PaymentCompletedEvent(orderId, paymentId, status);
        kafkaTemplate.send("payment.completed", event);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PaymentCompletedEvent {
        private UUID orderId;
        private String paymentId;
        private String status;
    }
}
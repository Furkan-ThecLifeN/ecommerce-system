package com.ecommerce.notification.event;

import com.ecommerce.notification.model.NotificationPayload;
import com.ecommerce.notification.service.EmailNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentCompletedEventListener {

    private final EmailNotificationService emailService;

    @KafkaListener(topics = "payment.completed", groupId = "notification-group")
    public void consume(ConsumerRecord<String, Object> record) { // Parametreyi ConsumerRecord yaptık
        try {
            // Asıl veriyi (value) alıp Map'e çeviriyoruz
            Map<String, Object> data = (Map<String, Object>) record.value();
            
            log.info("--------------------------------------------------");
            log.info("Kafka'dan veri başarıyla okundu! Order ID: {}", data.get("orderId"));

            NotificationPayload payload = NotificationPayload.builder()
                    .recipient("customer@example.com")
                    .subject("Ödeme Onayı")
                    .message("Siparişiniz başarıyla alındı. No: " + data.get("orderId"))
                    .type("EMAIL")
                    .build();

            emailService.sendEmail(payload);
            
        } catch (Exception e) {
            log.error("Veri işleme hatası: {}", e.getMessage());
        }
    }
}
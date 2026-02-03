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
public class UserCreatedEventListener {

    private final EmailNotificationService emailService;

    @KafkaListener(topics = "user.created", groupId = "notification-group")
    public void consume(ConsumerRecord<String, Object> record) {
        try {
            // Kafka zarfından veriyi (Map olarak) çıkartıyoruz
            @SuppressWarnings("unchecked")
            Map<String, Object> eventData = (Map<String, Object>) record.value();

            log.info("--------------------------------------------------");
            log.info("Yeni Kullanıcı Kaydı Bildirimi Alındı: {}", eventData.get("email"));

            // Bildirim içeriğini hazırlıyoruz
            NotificationPayload payload = NotificationPayload.builder()
                    .recipient(eventData.get("email").toString())
                    .subject("Aramıza Hoş Geldiniz!")
                    .message("Merhaba " + eventData.get("firstName") + ", hesabınız başarıyla oluşturuldu.")
                    .type("EMAIL")
                    .build();

            // Simüle edilmiş email gönderme işlemi
            emailService.sendEmail(payload);

        } catch (Exception e) {
            log.error("Kullanıcı kaydı bildirimi işlenirken hata oluştu: {}", e.getMessage());
        }
    }
}
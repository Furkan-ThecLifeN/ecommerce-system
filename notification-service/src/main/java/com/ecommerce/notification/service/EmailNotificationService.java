package com.ecommerce.notification.service;

import com.ecommerce.notification.model.NotificationPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailNotificationService {

    public void sendEmail(NotificationPayload payload) {
        log.info("--------------------------------------------------");
        log.info("BİLDİRİM GÖNDERİLDİ!");
        log.info("Kime: {}", payload.getRecipient());
        log.info("Konu: {}", payload.getSubject());
        log.info("Mesaj: {}", payload.getMessage());
        log.info("--------------------------------------------------");
    }
}
package com.ecommerce.notification.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationPayload {
    private String recipient;
    private String subject;
    private String message;
    private String type; // EMAIL, SMS, PUSH
}
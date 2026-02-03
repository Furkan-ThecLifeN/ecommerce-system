package com.ecommerce.common.event;

import lombok.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentCompletedEvent {
    private int version = 1; 
    private UUID orderId;
    private String paymentId;
    private String status;
}
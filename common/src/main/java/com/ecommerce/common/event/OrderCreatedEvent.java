package com.ecommerce.common.event;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderCreatedEvent(
    UUID orderId,
    BigDecimal totalPrice
) {}
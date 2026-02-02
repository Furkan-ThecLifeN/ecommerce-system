package com.ecommerce.common.event;

public record UserRegisteredEvent(
    Long userId,
    String email,
    String role
) {}

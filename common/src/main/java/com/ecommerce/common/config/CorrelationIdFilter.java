package com.ecommerce.common.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class CorrelationIdFilter extends OncePerRequestFilter {

    // Döküman 5.3.1: Header adı X-Request-Id olmalı
    private static final String CORRELATION_ID_HEADER = "X-Request-Id";
    private static final String MDC_KEY = "correlationId";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // Gateway'den gelen ID'yi yakala
        String correlationId = request.getHeader(CORRELATION_ID_HEADER);

        if (correlationId != null) {
            // Logback içinde %X{correlationId} ile yazdırabilmek için MDC'ye ekle
            MDC.put(MDC_KEY, correlationId);
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            // İstek bitince temizle (MDC ThreadLocal çalıştığı için temizlemek kritiktir)
            MDC.remove(MDC_KEY);
        }
    }
}
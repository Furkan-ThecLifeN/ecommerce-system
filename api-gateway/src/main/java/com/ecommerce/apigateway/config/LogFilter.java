package com.ecommerce.apigateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Component
public class LogFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(LogFilter.class);
    private static final String CORRELATION_ID_HEADER = "X-Request-Id"; // Döküman 5.3.1 gereği

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1. Benzersiz bir ID oluştur (İzlenebilirlik için ilk 8 karakter)
        String correlationId = UUID.randomUUID().toString().substring(0, 8);

        // 2. Bu ID'yi isteğin Header kısmına ekle (Diğer servislere taşınması için)
        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(r -> r.header(CORRELATION_ID_HEADER, correlationId))
                .build();

        // 3. Gateway loguna yaz (Hangi isteğin hangi ID ile başladığını görmek için)
        logger.info("**************************************************");
        logger.info("YENİ İSTEK GELDİ | Path: {} | ID: {}", 
                    exchange.getRequest().getPath(), correlationId);
        logger.info("**************************************************");

        return chain.filter(mutatedExchange);
    }

    @Override
    public int getOrder() {
        // Filtrenin en başta çalışması için en yüksek önceliği veriyoruz
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
package com.ecommerce.order.service;

import com.ecommerce.order.model.Order;
import com.ecommerce.order.repository.OrderRepository;
import com.ecommerce.common.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public Order placeOrder(Order order) {
        order.setStatus("CREATED");
        Order savedOrder = orderRepository.save(order);
        
        // Kafka'ya Sipariş Oluşturuldu Mesajı Atılıyor
        kafkaTemplate.send("order.created", new OrderCreatedEvent(
            savedOrder.getId(), 
            savedOrder.getTotalPrice()
        ));
        
        return savedOrder;
    }
}
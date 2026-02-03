package com.ecommerce.payment.service;

import com.ecommerce.payment.entity.Payment;
import com.ecommerce.payment.repository.PaymentRepository;
import com.ecommerce.payment.event.PaymentCompletedEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentCompletedEventProducer paymentCompletedEventProducer;

    @Transactional
    public void processPayment(Payment payment) {
        if (paymentRepository.existsByPaymentId(payment.getPaymentId())) {
            return;
        }

        paymentRepository.save(payment);

        paymentCompletedEventProducer.sendPaymentCompletedEvent(
                payment.getOrderId(),
                payment.getPaymentId(),
                payment.getStatus().name()
        );
    }
}

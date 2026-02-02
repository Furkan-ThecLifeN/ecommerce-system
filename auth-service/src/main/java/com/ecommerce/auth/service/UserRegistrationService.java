package com.ecommerce.auth.service;

import com.ecommerce.auth.model.User;
import com.ecommerce.auth.repository.UserRepository;
import com.ecommerce.common.event.UserRegisteredEvent;
import com.ecommerce.auth.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    private final UserRepository userRepository;
    private final KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate;

    public void registerUser(RegisterRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword()) 
                .role(request.getRole())
                .build();

        userRepository.save(user);

        kafkaTemplate.send("user.created", 
                new UserRegisteredEvent(
                        user.getId(),      
                        user.getEmail(),
                        user.getRole()
                )
        );
    }
}

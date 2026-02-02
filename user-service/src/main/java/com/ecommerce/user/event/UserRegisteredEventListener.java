package com.ecommerce.user.event;

import com.ecommerce.common.event.UserRegisteredEvent;
import com.ecommerce.user.model.UserProfile;
import com.ecommerce.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserRegisteredEventListener {

    private final UserProfileRepository profileRepository;

    @KafkaListener(topics = "user.created", groupId = "user-profile-group")
    public void handleUserRegistration(UserRegisteredEvent event) {
        log.info("Kafka'dan UserRegisteredEvent alındı: {}", event.email());

        UserProfile profile = UserProfile.builder()
                .id(new UUID(0L, event.userId()))
                .email(event.email())
                .role(event.role())
                .deleted(false)
                .build();

        profileRepository.save(profile);
        log.info("Kullanıcı profili kaydedildi: {}", profile.getEmail());
    }
}

package com.example.UserOrderManagement.Kafka;

import com.example.UserOrderManagement.Events.UserCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserEventProducer {
    private KafkaTemplate<String, UserCreatedEvent> kafkaTemplate;

    public UserEventProducer(KafkaTemplate<String, UserCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserCreatedEvent(UserCreatedEvent userCreatedEvent){
        System.out.println("Kafka producer sending event: " + userCreatedEvent);
        kafkaTemplate.send("user_created",userCreatedEvent);
    }
}

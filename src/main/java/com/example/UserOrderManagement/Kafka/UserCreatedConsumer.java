package com.example.UserOrderManagement.Kafka;
import com.example.UserOrderManagement.Events.UserCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserCreatedConsumer {
    @KafkaListener(topics="user_created",
            groupId = "user-service",
            concurrency = "1")
    public void consume(UserCreatedEvent userCreatedEvent){
        System.out.println("RECEIVED EVENT ");
        System.out.println("THE EVENT IS: "+userCreatedEvent);
    }
}

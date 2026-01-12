package com.example.UserOrderManagement.Kafka;

import com.example.UserOrderManagement.Events.UserCreatedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class UserKafkaListener {
    private final UserEventProducer userEventProducer;

    public UserKafkaListener(UserEventProducer userEventProducer) {
        this.userEventProducer = userEventProducer;
    }
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT) //listens for transaction to commit
    public void handleUserCreatedEvent(UserCreatedEvent userCreatedEvent){
        System.out.println("AFTER_COMMIT listener triggered"); //log to check
        userEventProducer.sendUserCreatedEvent(userCreatedEvent);
    }
}

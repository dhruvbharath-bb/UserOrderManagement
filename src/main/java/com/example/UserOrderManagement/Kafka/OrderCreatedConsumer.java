package com.example.UserOrderManagement.Kafka;

import com.example.UserOrderManagement.Events.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedConsumer {
    @KafkaListener(topics="order_created",
    groupId = "order-service",
    concurrency = "1")
    public void consume(OrderCreatedEvent orderCreatedEvent){
        System.out.println("RECEIVED EVENT ");
        System.out.println("THE EVENT IS: "+orderCreatedEvent);
    }
}

package com.example.UserOrderManagement.Kafka;

import com.example.UserOrderManagement.Events.OrderCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventProducer {
    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public OrderEventProducer(KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent){
        System.out.println("Kafka producer sending event: " + orderCreatedEvent);
        kafkaTemplate.send("order_created",orderCreatedEvent);
    }
}

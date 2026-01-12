package com.example.UserOrderManagement.Kafka;

import com.example.UserOrderManagement.Events.OrderCreatedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
@Component
public class OrderKafkaListener {
    private final OrderEventProducer orderEventProducer;

    public OrderKafkaListener(OrderEventProducer orderEventProducer) {
        this.orderEventProducer = orderEventProducer;
    }
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderCreated(OrderCreatedEvent event){
        System.out.println("AFTER_COMMIT listener triggered"); //log to check
        orderEventProducer.sendOrderCreatedEvent(event);
    }
}

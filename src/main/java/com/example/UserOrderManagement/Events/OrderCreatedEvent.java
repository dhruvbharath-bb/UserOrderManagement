package com.example.UserOrderManagement.Events;

import java.time.Instant;

public record OrderCreatedEvent(EventMetadata eventMetadata,
                                OrderCreatedPayload orderCreatedPayload) {
}

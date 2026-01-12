package com.example.UserOrderManagement.Events;

public record UserCreatedEvent(EventMetadata eventMetadata,
                               UserCreatedPayload userCreatedPayload) {
}

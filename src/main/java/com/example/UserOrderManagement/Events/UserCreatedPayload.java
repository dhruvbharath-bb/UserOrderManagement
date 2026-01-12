package com.example.UserOrderManagement.Events;

public record UserCreatedPayload(Long id,
                                 String name,
                                 String email) {
}

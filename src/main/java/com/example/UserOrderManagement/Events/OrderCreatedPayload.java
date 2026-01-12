package com.example.UserOrderManagement.Events;

public record OrderCreatedPayload(Long orderId,
                                  Long userId,
                                  String orderNumber,
                                  int amount) {
}

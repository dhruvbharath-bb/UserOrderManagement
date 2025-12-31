package com.example.UserOrderManagement.DTO;

import com.example.UserOrderManagement.Entity.User;

public record OrderResponseDTO(Long id,
                               String orderNumber,
                               int amount) {
}

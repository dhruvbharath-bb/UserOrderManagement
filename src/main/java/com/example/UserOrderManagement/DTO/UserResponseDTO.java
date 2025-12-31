package com.example.UserOrderManagement.DTO;

import com.example.UserOrderManagement.Entity.Order;

import java.util.List;

public record UserResponseDTO(Long id,
                              String name,
                              String email,
                              List<OrderResponseDTO> orders) {
}

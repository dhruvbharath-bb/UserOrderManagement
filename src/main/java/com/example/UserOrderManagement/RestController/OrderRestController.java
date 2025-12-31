package com.example.UserOrderManagement.RestController;

import com.example.UserOrderManagement.DTO.OrderRequestDTO;
import com.example.UserOrderManagement.DTO.OrderResponseDTO;
import com.example.UserOrderManagement.Service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class OrderRestController {
    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }
    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrdersByUserId(@PathVariable Long userId){
        List<OrderResponseDTO> orderResponseDTOS = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orderResponseDTOS);
    }
    @GetMapping("/{userId}/orders/{orderId}")
    public ResponseEntity<OrderResponseDTO> getByOrderIdAndUserId(@PathVariable Long userId, @PathVariable Long orderId){
        OrderResponseDTO orderResponseDTO = orderService.getByOrderIdAndUserId(userId,orderId);
        return ResponseEntity.ok(orderResponseDTO);
    }
    @PostMapping("/{userId}/orders")
    public ResponseEntity<OrderResponseDTO> createOrderByUserId(@RequestBody OrderRequestDTO orderRequestDTO, @PathVariable Long userId){
        OrderResponseDTO orderResponseDTO = orderService.createOrder(orderRequestDTO,userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponseDTO);
    }
}

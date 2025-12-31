package com.example.UserOrderManagement.Service;

import com.example.UserOrderManagement.DTO.OrderRequestDTO;
import com.example.UserOrderManagement.DTO.OrderResponseDTO;
import com.example.UserOrderManagement.Entity.Order;
import com.example.UserOrderManagement.Entity.User;
import com.example.UserOrderManagement.Exception.ResourceNotFoundException;
import com.example.UserOrderManagement.Repository.OrderRepository;
import com.example.UserOrderManagement.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository){
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getOrdersByUserId(Long userId){
        Optional<User> checkUser = userRepository.findById(userId);
        if(checkUser.isEmpty()){
            throw new ResourceNotFoundException("User is not found with ID: "+userId);
        }
        List<Order> orders = orderRepository.findByUserId(userId);
        List<OrderResponseDTO> orderResponseDTOS = new ArrayList<>();
        for(Order order:orders){
            OrderResponseDTO orderResponseDTO = new OrderResponseDTO(order.getId(),
                    order.getOrderNumber(),
                    order.getAmount());
            orderResponseDTOS.add(orderResponseDTO);
        }
        return orderResponseDTOS;
    }
    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO, Long userId){
        Optional<User> checkUser = userRepository.findById(userId);
        if(checkUser.isEmpty()){
            throw new ResourceNotFoundException("User is not found with ID: "+userId);
        }
        User user = checkUser.get();
        Order order = new Order();
        order.setOrderNumber(orderRequestDTO.orderNumber());
        order.setAmount(orderRequestDTO.amount());
        order.setUser(user);

        Order savedOrder = orderRepository.save(order);
        return new OrderResponseDTO(savedOrder.getId(),
                savedOrder.getOrderNumber(),
                savedOrder.getAmount());
    }
    @Transactional(readOnly = true)
    public OrderResponseDTO getByOrderIdAndUserId(Long userId, Long orderId){
        Optional<User> checkUser = userRepository.findById(userId);
        if(checkUser.isEmpty()){
            throw new ResourceNotFoundException("User is not found with ID: "+userId);
        }
        Optional<Order> checkOrder = orderRepository.findByIdAndUserId(orderId,userId);
        if(checkOrder.isEmpty()){
            throw new ResourceNotFoundException("User is not found with ID: "+orderId);
        }
        Order order = checkOrder.get();
        return new OrderResponseDTO(order.getId(),
                order.getOrderNumber(),
                order.getAmount());

    }
}

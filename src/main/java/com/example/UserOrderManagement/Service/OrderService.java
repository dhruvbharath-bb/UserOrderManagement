package com.example.UserOrderManagement.Service;

import com.example.UserOrderManagement.DTO.OrderRequestDTO;
import com.example.UserOrderManagement.DTO.OrderResponseDTO;
import com.example.UserOrderManagement.Entity.Order;
import com.example.UserOrderManagement.Entity.User;
import com.example.UserOrderManagement.Events.EventMetadata;
import com.example.UserOrderManagement.Events.OrderCreatedEvent;
import com.example.UserOrderManagement.Events.OrderCreatedPayload;
import com.example.UserOrderManagement.Exception.ResourceNotFoundException;
import com.example.UserOrderManagement.Kafka.OrderEventProducer;
import com.example.UserOrderManagement.Repository.OrderRepository;
import com.example.UserOrderManagement.Repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    public OrderService(OrderRepository orderRepository, UserRepository userRepository, ApplicationEventPublisher applicationEventPublisher){
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }
    @Cacheable(value="orders", key = "#p0")
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getOrdersByUserId(Long userId){
        Optional<User> checkUser = userRepository.findById(userId);
        if(checkUser.isEmpty()){
            throw new ResourceNotFoundException("User is not found with ID: "+userId);
        }
        System.out.println("HITTING DATABASE for user " + userId); //log
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
    @CacheEvict(value ="orders",key="#p1")
    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO, Long userId){
        Optional<User> checkUser = userRepository.findById(userId);
        if(checkUser.isEmpty()){
            throw new ResourceNotFoundException("User is not found with ID: "+userId);
        }
        System.out.println("Evicting old cache data");//log
        User user = checkUser.get();

        //Creating new Order with OrderRequestDTO
        Order order = new Order();
        order.setOrderNumber(orderRequestDTO.orderNumber());
        order.setAmount(orderRequestDTO.amount());
        order.setUser(user);

        //Save entity to repo
        Order savedOrder = orderRepository.save(order);

        //Generating a Event
        EventMetadata eventMetadata = new EventMetadata(UUID.randomUUID(),
                "OrderCreated",
                Instant.now(),
                "order-service",
                1);

        OrderCreatedPayload orderCreatedPayload = new OrderCreatedPayload(savedOrder.getId(),
                savedOrder.getUser().getId(),
                savedOrder.getOrderNumber(),
                savedOrder.getAmount());

        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent(eventMetadata,orderCreatedPayload);
        applicationEventPublisher.publishEvent(orderCreatedEvent);
        //Return new OrderDTO
        return new OrderResponseDTO(savedOrder.getId(),
                savedOrder.getOrderNumber(),
                savedOrder.getAmount());
    }
    @Cacheable(value = "orders",key = "#p1")
    @Transactional(readOnly = true)
    public OrderResponseDTO getByOrderIdAndUserId(Long userId, Long orderId){
        Optional<User> checkUser = userRepository.findById(userId);
        if(checkUser.isEmpty()){
            throw new ResourceNotFoundException("User is not found with ID: "+userId);
        }
        Optional<Order> checkOrder = orderRepository.findByIdAndUserId(orderId,userId);
        System.out.println("HITTING DATABASE for user: " + userId+" and order: "+orderId); //log
        if(checkOrder.isEmpty()){
            throw new ResourceNotFoundException("User is not found with ID: "+orderId);
        }
        Order order = checkOrder.get();
        return new OrderResponseDTO(order.getId(),
                order.getOrderNumber(),
                order.getAmount());
    }
}

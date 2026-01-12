package com.example.UserOrderManagement.Service;

import com.example.UserOrderManagement.DTO.OrderResponseDTO;
import com.example.UserOrderManagement.DTO.UserRequestDTO;
import com.example.UserOrderManagement.DTO.UserResponseDTO;
import com.example.UserOrderManagement.DTO.UserSummaryDTO;
import com.example.UserOrderManagement.Entity.Order;
import com.example.UserOrderManagement.Entity.User;
import com.example.UserOrderManagement.Events.EventMetadata;
import com.example.UserOrderManagement.Events.UserCreatedEvent;
import com.example.UserOrderManagement.Events.UserCreatedPayload;
import com.example.UserOrderManagement.Exception.ResourceNotFoundException;
import com.example.UserOrderManagement.Repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final UserRepository userRepository;
    public UserService(ApplicationEventPublisher applicationEventPublisher, UserRepository userRepository) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.userRepository = userRepository;
    }
    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO){
        User createUser = new User();
        createUser.setEmail(userRequestDTO.email());
        createUser.setName(userRequestDTO.name());
        User savedUser = userRepository.save(createUser);

        //Generating an event
        EventMetadata eventMetadata = new EventMetadata(UUID.randomUUID(),
                "UserCreated",
                Instant.now(),
                "user-service",
                1);
        UserCreatedPayload userCreatedPayload = new UserCreatedPayload(savedUser.getId(),
                savedUser.getName(),
                savedUser.getName());
        applicationEventPublisher.publishEvent(new UserCreatedEvent(eventMetadata,userCreatedPayload));

        //returning DTO
        return new UserResponseDTO(savedUser.getId(),
                savedUser.getName(), savedUser.getEmail(), List.of());
    }
    @Cacheable(value = "users", key = "#id") //Creates cache with key-value
    @Transactional(readOnly = true)
    public UserResponseDTO getUserWithOrders(Long id){
        Optional<User> checkUser = userRepository.findById(id);
        if(checkUser.isEmpty()){
            throw new ResourceNotFoundException("Cannot find user with ID: "+id);
        }
        User user = checkUser.get();
        List<Order> orders = user.getOrders();
        List<OrderResponseDTO> orderResponseDTOS = new ArrayList<>();
        for(Order order:orders){
            OrderResponseDTO orderResponseDTO = new OrderResponseDTO(order.getId(),
                    order.getOrderNumber(),
                    order.getAmount());
            orderResponseDTOS.add(orderResponseDTO);
        }

        System.out.println("HITTING DATABASE for user: "+ id); //log
        return new UserResponseDTO(user.getId(),
                user.getName(),
                user.getEmail(),
                orderResponseDTOS);
    }

    @Transactional(readOnly = true)
    public List<UserSummaryDTO> getAllUsers(){ //not cached as it gets all users..
        List<User> allUsers = userRepository.findAll();
        List<UserSummaryDTO> userSummaryDTOS = new ArrayList<>();
        for (User user:allUsers){
            UserSummaryDTO userSummaryDTO = new UserSummaryDTO(user.getId(),
                    user.getName(),
                    user.getEmail());
            userSummaryDTOS.add(userSummaryDTO);
        }
        return userSummaryDTOS;
    }
    @CacheEvict(value = "users",key = "#id") //Stale cache is evicted
    @Transactional
    public void deleteUserById(Long id){
        Optional<User> checkUser = userRepository.findById(id);
        if(checkUser.isEmpty()){
            throw new ResourceNotFoundException("User is not found with id: " + id);
        }
        System.out.println("Cache is evicted with userid: " + id);
        userRepository.deleteById(id);
    }
    @CacheEvict(value = "users",key = "#id")    //Stale cache is evicted
    @Transactional
    public UserResponseDTO updateUserById(UserRequestDTO userRequestDTO, Long id){
        Optional<User> checkUser = userRepository.findById(id);
        if(checkUser.isEmpty()){
            throw new ResourceNotFoundException("User is not found with id: " + id);
        }
        System.out.println("Cache is evicted with userid: " + id);
        User user = checkUser.get();
        user.setName(userRequestDTO.name());
        user.setName(userRequestDTO.email());
        User updatedUser = userRepository.save(user);

        return new UserResponseDTO(updatedUser.getId(),
                updatedUser.getName(),
                updatedUser.getEmail(),
                List.of());
    }
}

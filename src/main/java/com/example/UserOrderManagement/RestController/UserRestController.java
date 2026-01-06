package com.example.UserOrderManagement.RestController;

import com.example.UserOrderManagement.DTO.UserRequestDTO;
import com.example.UserOrderManagement.DTO.UserResponseDTO;
import com.example.UserOrderManagement.DTO.UserSummaryDTO;
import com.example.UserOrderManagement.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id){
        UserResponseDTO userResponseDTO = userService.getUserWithOrders(id);
        return ResponseEntity.ok(userResponseDTO);
    }
    @GetMapping
    public ResponseEntity<List<UserSummaryDTO>> getAllUsers(){
        List<UserSummaryDTO> userSummaryDTOS = userService.getAllUsers();
        return ResponseEntity.ok(userSummaryDTOS);
    }
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO){
        UserResponseDTO userResponseDTO = userService.createUser(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUserById(@RequestBody UserRequestDTO userRequestDTO, @PathVariable Long id){
        UserResponseDTO userResponseDTO = userService.updateUserById(userRequestDTO,id);
        return ResponseEntity.ok(userResponseDTO);
    }
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);
    }
}
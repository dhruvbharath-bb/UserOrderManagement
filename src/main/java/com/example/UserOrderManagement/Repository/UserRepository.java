package com.example.UserOrderManagement.Repository;

import com.example.UserOrderManagement.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

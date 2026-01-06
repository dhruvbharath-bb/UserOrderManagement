package com.example.UserOrderManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching // Enables Spring's caching
public class UserOrderManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserOrderManagementApplication.class, args);
	}

}

package com.example.UserOrderManagement.RestController;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class UserOrderController {

    private final RestTemplate restTemplate;
    @Value("${server.port:8080}")
    private String port;

    public UserOrderController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @GetMapping("/ping")
    public String ping() throws Exception{
        return "Port from: " + port;
    }
    @GetMapping("/call-myself")
    public String callMyself(){
        String result = restTemplate.getForObject("http://user-order-management/ping",String.class);
        return "Caller port = " + port + " | Response = " + result;
    }
}

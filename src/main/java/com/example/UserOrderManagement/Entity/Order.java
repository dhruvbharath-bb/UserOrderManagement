package com.example.UserOrderManagement.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNumber;
    private int amount;
    @ManyToOne(fetch = FetchType.LAZY) //Lazy Loading
    @JoinColumn(name="user_id") //foreign key
    private User user;

    public Order(){}

    public Order(String orderNumber, int amount){
        this.orderNumber = orderNumber;
        this.amount = amount;
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

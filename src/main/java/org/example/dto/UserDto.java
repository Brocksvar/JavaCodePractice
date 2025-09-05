package org.example.dto;

import org.example.entity.User;

import java.util.List;
import java.util.UUID;

public class UserDto {
    private UUID id;
    private String name;
    private String email;
    private List<OrderDto> orders;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<OrderDto> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDto> orders) {
        this.orders = orders;
    }

    public User toEntity() {
        User user = new User();
        user.setId(this.id);
        user.setName(this.name);
        user.setEmail(this.email);

        if (this.orders != null) {
            user.setOrders(
                    this.orders.stream()
                            .map(OrderDto::toEntity)
                            .toList()
            );
        }
        return user;
    }
}


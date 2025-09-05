package org.example.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import org.example.dto.UserDto;
import org.example.view.Views;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user")
public class User {
    @JsonView(Views.UserSummary.class)
    private UUID id;

    @JsonView(Views.UserSummary.class)
    private String name;

    @Email(message = "Некорректный формат email")
    @JsonView(Views.UserSummary.class)
    private String email;

    @JsonView(Views.UserDetails.class)
    private List<Order> orders;

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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public UserDto fromEntity() {
        UserDto dto = new UserDto();
        dto.setId(this.getId());
        dto.setName(this.getName());
        dto.setEmail(this.getEmail());

        if (this.getOrders() != null) {
            dto.setOrders(
                    this.getOrders().stream()
                            .map(Order::fromEntity)
                            .toList()
            );
        }
        return dto;
    }
}

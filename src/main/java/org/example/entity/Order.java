package org.example.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.example.view.Views;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "order")
public class Order {

    @JsonView(Views.UserDetails.class)
    private UUID id;

    @JsonView(Views.UserDetails.class)
    private List<String> products;

    @JsonView(Views.UserDetails.class)
    private Double cost;

    @JsonView(Views.UserDetails.class)
    private String status;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

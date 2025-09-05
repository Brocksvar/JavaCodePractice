package org.example.dto;

import org.example.entity.Order;

import java.util.List;
import java.util.UUID;

public class OrderDto {
    private UUID id;
    private List<String> products;
    private Double cost;
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

    public Order toEntity() {
        Order order = new Order();
        order.setId(this.id);
        order.setProducts(this.products);
        order.setCost(this.cost);
        order.setStatus(this.status);
        return order;
    }
}

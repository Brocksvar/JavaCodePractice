package org.example.dto;

import org.example.entity.Order;
import org.example.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderDto(
        UUID orderId,
        CustomerDto customer,
        List<ProductDto> products,
        LocalDateTime orderDate,
        String shippingAddress,
        Double totalPrice,
        OrderStatus orderStatus
) {
    public Order mapToOrder() {
        Order order = new Order();
        order.setOrderId(this.orderId);
        order.setCustomer(this.customer().mapToCustomer());
        order.setProducts(this.products.stream().map(ProductDto::mapToProduct).toList());
        order.setOrderDate(this.orderDate);
        order.setShippingAddress(this.shippingAddress);
        order.setTotalPrice(this.totalPrice);
        order.setOrderStatus(this.orderStatus);
        return order;
    }
}

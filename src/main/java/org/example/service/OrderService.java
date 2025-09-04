package org.example.service;

import org.example.dao.CustomerRepository;
import org.example.dao.OrderRepository;
import org.example.dao.ProductRepository;
import org.example.entity.Customer;
import org.example.entity.Order;
import org.example.entity.Product;
import org.example.enums.OrderStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository,
                        CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    public Order createOrder(Order orderRequest) {
        Customer customer = customerRepository.save(orderRequest.getCustomer());

        List<Product> products = productRepository.findAllById(
                orderRequest.getProducts().stream().map(Product::getProductId).toList()
        );

        double totalPrice = products.stream()
                .mapToDouble(Product::getPrice)
                .sum();

        Order order = new Order();
        order.setCustomer(customer);
        order.setProducts(products);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(orderRequest.getShippingAddress());
        order.setTotalPrice(totalPrice);
        order.setOrderStatus(OrderStatus.NEW);

        return orderRepository.save(order);
    }

    public Order getOrderById(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Заказ с id=" + id + " не найден"));
    }
}

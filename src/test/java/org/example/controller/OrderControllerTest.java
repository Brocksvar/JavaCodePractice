package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.OrderDto;
import org.example.entity.Customer;
import org.example.entity.Order;
import org.example.entity.Product;
import org.example.enums.OrderStatus;
import org.example.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OrderService orderService;

    @Test
    void testCreateOrder() throws Exception {
        UUID product1Id = UUID.randomUUID();
        UUID product2Id = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();

        String orderJson = """
                {
                  "customer": {
                    "customerId": "%s",
                    "firstName": "Иван",
                    "lastName": "Иванов",
                    "email": "ivan@example.com",
                    "contactNumber": "+79990001122"
                  },
                  "products": [
                    {"productId": "%s"},
                    {"productId": "%s"}
                  ],
                  "shippingAddress": "Москва, ул. Ленина, 10"
                }
                """.formatted(customerId, product1Id, product2Id);

        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setFirstName("Иван");
        customer.setLastName("Иванов");
        customer.setEmail("ivan@example.com");
        customer.setContactNumber("+79990001122");

        Product p1 = new Product();
        p1.setProductId(product1Id);
        p1.setName("Товар 1");
        p1.setPrice(100.0);

        Product p2 = new Product();
        p2.setProductId(product2Id);
        p2.setName("Товар 2");
        p2.setPrice(200.0);

        Order savedOrder = new Order();
        savedOrder.setOrderId(orderId);
        savedOrder.setCustomer(customer);
        savedOrder.setProducts(List.of(p1, p2));
        savedOrder.setOrderDate(LocalDateTime.now());
        savedOrder.setShippingAddress("Москва, ул. Ленина, 10");
        savedOrder.setTotalPrice(300.0);
        savedOrder.setOrderStatus(OrderStatus.NEW);

        when(orderService.createOrder(any(OrderDto.class))).thenReturn(savedOrder.mapToOrderDto());

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.orderId").value(orderId.toString()))
                .andExpect(jsonPath("$.customer.firstName").value("Иван"))
                .andExpect(jsonPath("$.products[0].name").value("Товар 1"))
                .andExpect(jsonPath("$.totalPrice").value(300.0));
    }

    @Test
    void testGetOrderById() throws Exception {
        UUID productId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();

        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setFirstName("Петр");
        customer.setLastName("Петров");
        customer.setEmail("petr@example.com");
        customer.setContactNumber("+79995553322");

        Product product = new Product();
        product.setProductId(productId);
        product.setName("Телефон");
        product.setPrice(500.0);

        Order order = new Order();
        order.setOrderId(orderId);
        order.setCustomer(customer);
        order.setProducts(List.of(product));
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress("Санкт-Петербург, Невский 1");
        order.setTotalPrice(500.0);
        order.setOrderStatus(OrderStatus.PROCESSING);

        when(orderService.getOrderById(orderId)).thenReturn(order.mapToOrderDto());

        mockMvc.perform(get("/api/orders/" + orderId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.orderId").value(orderId.toString()))
                .andExpect(jsonPath("$.customer.lastName").value("Петров"))
                .andExpect(jsonPath("$.products[0].name").value("Телефон"))
                .andExpect(jsonPath("$.orderStatus").value("PROCESSING"));
    }
}

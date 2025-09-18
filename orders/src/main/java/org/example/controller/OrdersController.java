package org.example.controller;

import org.example.dto.OrderDto;
import org.example.kafka.service.KafkaProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrdersController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final KafkaProducerService kafkaProducerService;

    @Value(value = "${kafka.topics.new-orders-topic}")
    private String newOrdersTopic;

    public OrdersController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @GetMapping("/order")
    public void createOrder(@RequestBody OrderDto orderDto) {
        logger.info("Получен запрос на добавление нового заказа: {}", orderDto);
        kafkaProducerService.sendMessage(newOrdersTopic, orderDto);
    }
}

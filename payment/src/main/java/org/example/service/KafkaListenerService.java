package org.example.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.dto.OrderDto;
import org.example.kafka.service.KafkaProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaListenerService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final KafkaProducerService kafkaProducerService;

    @Value(value = "${kafka.topics.payed-orders-topic}")
    private String payedOrdersTopic;

    public KafkaListenerService(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @KafkaListener(
            topics = "${kafka.topics.new-orders-topic}",
            groupId = "payment",
            concurrency = "${kafka.topics.concurrency}")
    public void listenGroupFoo(ConsumerRecord<String, OrderDto> consumerRecord) {
        OrderDto orderDto = consumerRecord.value();
        logger.info("Получено сообщение из топика: {}, offset: {}. [{}]",
                consumerRecord.topic(),
                consumerRecord.offset(),
                orderDto);
        orderDto.setPaymentSuccess(true);
        logger.info("Заказ успешно оплачен: {}", orderDto);
        kafkaProducerService.sendMessage(payedOrdersTopic, orderDto);
    }
}

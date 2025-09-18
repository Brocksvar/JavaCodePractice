package org.example.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.dto.OrderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaListenerService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @KafkaListener(
            topics = "${kafka.topics.sent-orders-topic}",
            groupId = "notifications",
            concurrency = "${kafka.topics.concurrency}")
    public void listenGroupFoo(ConsumerRecord<String, OrderDto> consumerRecord) {
        OrderDto orderDto = consumerRecord.value();
        logger.info("Получено сообщение из топика: {}, offset: {}. [{}]",
                consumerRecord.topic(),
                consumerRecord.offset(),
                orderDto);
        logger.info("Заказ был успешно доставлен: {}", orderDto);
    }
}

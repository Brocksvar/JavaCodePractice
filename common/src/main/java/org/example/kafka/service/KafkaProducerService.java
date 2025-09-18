package org.example.kafka.service;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, Object objectToSend) {
        ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(topic, objectToSend);
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(producerRecord);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                logger.info("Отправлено сообщение={}, topic={}, offset={}",
                        objectToSend,
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().offset());
            } else {
                logger.error("Ошибка при отправке сообщения={}. Ошибка: {}", objectToSend, ex.getMessage());
            }
        });
    }
}

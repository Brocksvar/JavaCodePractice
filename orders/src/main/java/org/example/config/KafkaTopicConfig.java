package org.example.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${kafka.topics.new-orders-topic}")
    private String newOrdersTopic;

    @Value(value = "${kafka.topics.payed-orders-topic}")
    private String payedOrdersTopic;

    @Value(value = "${kafka.topics.sent-orders-topic}")
    private String sentOrdersTopic;

    @Value(value = "${kafka.topics.partition-count}")
    private int partitionCount;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic newOrdersTopic() {
        return TopicBuilder.name(newOrdersTopic)
                .partitions(partitionCount)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic payedOrdersTopic() {
        return TopicBuilder.name(payedOrdersTopic)
                .partitions(partitionCount)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic sentOrdersTopic() {
        return TopicBuilder.name(sentOrdersTopic)
                .partitions(partitionCount)
                .replicas(1)
                .build();
    }
}

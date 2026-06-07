package com.libverse.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    @Value("${kafka.mail.topic}")
    private String mailTopic;

    @Value("${kafka.mail.partitions}")
    private Integer mailPartitions;

    @Value("${kafka.mail.replicas}")
    private Integer mailReplicas;

    @Bean
    public NewTopic mailTopic() {
        return TopicBuilder.name(mailTopic)
                .partitions(mailPartitions)
                .replicas(mailReplicas)
                .build();
    }
}

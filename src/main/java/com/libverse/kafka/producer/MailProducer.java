package com.libverse.kafka.producer;

import com.libverse.kafka.dto.MailMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class MailProducer {
    private final KafkaTemplate<String, MailMessage> kafkaTemplate;

    @Value("${kafka.mail.topic}")
    private String mailTopic;

    public void send(String to, String subject, String template, Map<String, Object> variables) {
        MailMessage message = MailMessage.builder()
                .to(to)
                .subject(subject)
                .template(template)
                .variables(variables)
                .build();

        kafkaTemplate.send(mailTopic, message);
    }
}

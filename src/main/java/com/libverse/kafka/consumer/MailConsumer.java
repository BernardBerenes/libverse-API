package com.libverse.kafka.consumer;

import com.libverse.kafka.dto.MailMessage;
import com.libverse.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailConsumer {
    private final MailService mailService;

    @KafkaListener(topics = "${kafka.mail.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(MailMessage message) {
        mailService.send(message.getTo(), message.getSubject(), message.getTemplate(), message.getVariables());
    }
}

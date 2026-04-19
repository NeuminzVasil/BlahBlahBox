package com.home.service;

import com.home.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {
    private final MessageService messageService;

    @KafkaListener(topics = "chat-messages")
    public void consume(Message message) {
        messageService.processMessage(message);
    }
}
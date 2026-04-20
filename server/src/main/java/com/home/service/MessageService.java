package com.home.service;

import com.home.model.Message;
import com.home.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final KafkaTemplate<String, Message> kafkaTemplate;
    private final SimpMessagingTemplate webSocket;
    private final MessageRepository messageRepository;

    public void sendMessage(Message message) {
        message.setTimestamp(LocalDateTime.now());
        messageRepository.save(message);
        kafkaTemplate.send("chat-messages", message);
    }

    public void processMessage(Message message) {
        String destination = message.getToUser() == null
                ? "/topic/public"
                : "/topic/private/" + message.getToUser();
        webSocket.convertAndSend(destination, message);
    }
}
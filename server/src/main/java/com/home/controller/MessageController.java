package com.home.controller;

import com.home.dto.MessageRequest;
import com.home.model.Message;
import com.home.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/api/message")
    public void sendMessage(@RequestBody MessageRequest request, Authentication auth) {
        Message message = new Message();
        message.setFromUser(auth.getName());
        message.setToUser(request.getToUser());
        message.setContent(request.getContent());
        messageService.sendMessage(message);
    }
}
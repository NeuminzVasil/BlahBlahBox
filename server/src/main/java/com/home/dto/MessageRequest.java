package com.home.dto;

import lombok.Data;

@Data
public class MessageRequest {
    private String toUser;
    private String content;
}
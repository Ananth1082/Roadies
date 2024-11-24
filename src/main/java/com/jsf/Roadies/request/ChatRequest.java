package com.jsf.Roadies.request;

import lombok.Data;

@Data
public class ChatRequest {
    private String message;
    private Long senderId;
    private Long squadId;
}

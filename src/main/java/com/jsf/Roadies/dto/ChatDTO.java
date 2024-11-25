package com.jsf.Roadies.dto;

import com.jsf.Roadies.model.UserSquad;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatDTO {
    private Long id;
    private String message;
    private LocalDateTime sentAt;
    private Long userId;
    private Long squadId;
}

package com.jsf.Roadies.dto;

import com.jsf.Roadies.model.UserSquad;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SquadDTO {
    private Long id;
    private String squadName;
    private String squadDescription;
    private int squadCapacity;
    private int squadRange;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isExpired;
}

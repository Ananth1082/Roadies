package com.jsf.Roadies.dto;

import com.jsf.Roadies.model.UserSquad;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class SquadDTO {
    private Long id;
    private String squadName;
    private String squadDescription;
    private int squadCapacity;
    private int squadRange;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Boolean isExpired;
    private List<UserSquad> userSquads;
}

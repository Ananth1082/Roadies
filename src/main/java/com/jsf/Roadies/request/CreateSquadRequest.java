package com.jsf.Roadies.request;

import com.jsf.Roadies.model.UserSquad;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class CreateSquadRequest {
    private Long userId;
    private String squadName;
    private String squadDescription;
    private int squadCapacity;
    private int squadRange;
}

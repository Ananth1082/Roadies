package com.jsf.Roadies.request;

import com.jsf.Roadies.model.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateLocationRequest {
    private Double latitude;
    private Double longitude;
    private Long userId;
    private LocalDateTime time;
}

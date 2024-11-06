package com.jsf.Roadies.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
public class UserDTO {
    private Long id;
    private String name;
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String bio;
}

package com.jsf.Roadies.request;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String username;
    private String password;
    private String phoneNumber;
    private String bio;
}

package com.jsf.Roadies.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String phoneNumber;
    private String password;
}

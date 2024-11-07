package com.jsf.Roadies.Exceptions;

public class MemberAlreadyExists extends RuntimeException {
    public MemberAlreadyExists(String message) {
        super(message);
    }
}

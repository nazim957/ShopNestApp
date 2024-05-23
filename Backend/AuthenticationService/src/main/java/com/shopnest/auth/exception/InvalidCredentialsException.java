package com.shopnest.auth.exception;

public class InvalidCredentialsException extends RuntimeException {

    private final String customMessage;

    public InvalidCredentialsException(String customMessage) {
        super(customMessage);
        this.customMessage = customMessage;
    }

    public String getCustomMessage() {
        return customMessage;
    }
}

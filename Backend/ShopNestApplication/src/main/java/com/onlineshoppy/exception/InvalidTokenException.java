package com.onlineshoppy.exception;

public class InvalidTokenException extends Exception{
    private final String customMessage;

    public InvalidTokenException(String customMessage) {
        super(customMessage);
        this.customMessage = customMessage;
    }

    public String getCustomMessage() {
        return customMessage;
    }
}

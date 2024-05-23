package com.shopnest.user.exception;


public class UserNotVerifiedException extends Exception {
    private final String customMessage;

    public UserNotVerifiedException(String customMessage) {
        super(customMessage);
        this.customMessage = customMessage;
    }

    public String getCustomMessage() {
        return customMessage;
    }
}
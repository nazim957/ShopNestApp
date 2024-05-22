package com.newsapp.user.exception;

@SuppressWarnings("serial")
public class UserNotFoundException extends Exception {
    private final String customMessage;

    public UserNotFoundException(String customMessage) {
        super(customMessage);
        this.customMessage = customMessage;
    }

    public String getCustomMessage() {
        return customMessage;
    }

}

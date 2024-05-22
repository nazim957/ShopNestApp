package com.newsapp.user.exception;

@SuppressWarnings("serial")
public class UserAlreadyExistException extends Exception {

    private final String customMessage;

    public UserAlreadyExistException(String customMessage) {
        super(customMessage);
        this.customMessage = customMessage;
    }

    public String getCustomMessage() {
        return customMessage;
    }
}

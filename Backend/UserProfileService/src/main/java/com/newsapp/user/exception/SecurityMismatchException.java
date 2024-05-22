package com.newsapp.user.exception;

@SuppressWarnings("serial")
public class SecurityMismatchException extends Exception {
    private final String customMessage;

    public SecurityMismatchException(String customMessage) {
        super(customMessage);
        this.customMessage = customMessage;
    }

    public String getCustomMessage() {
        return customMessage;
    }
}

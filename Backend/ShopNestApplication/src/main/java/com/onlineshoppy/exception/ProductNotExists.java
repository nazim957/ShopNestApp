package com.onlineshoppy.exception;

public class ProductNotExists extends Exception{

    private final String customMessage;

    public ProductNotExists(String customMessage) {
        super(customMessage);
        this.customMessage = customMessage;
    }

    public String getCustomMessage() {
        return customMessage;
    }
}
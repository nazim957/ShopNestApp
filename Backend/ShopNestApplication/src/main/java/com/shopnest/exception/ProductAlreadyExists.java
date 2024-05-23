package com.shopnest.exception;

public class ProductAlreadyExists extends Exception{

    private final String customMessage;

    public ProductAlreadyExists(String customMessage) {
        super(customMessage);
        this.customMessage = customMessage;
    }

    public String getCustomMessage() {
        return customMessage;
    }
}
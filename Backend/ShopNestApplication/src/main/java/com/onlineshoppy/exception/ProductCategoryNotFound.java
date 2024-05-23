package com.onlineshoppy.exception;

public class ProductCategoryNotFound extends Exception{
    private final String customMessage;

    public ProductCategoryNotFound(String customMessage) {
        super(customMessage);
        this.customMessage = customMessage;
    }

    public String getCustomMessage() {
        return customMessage;
    }
}

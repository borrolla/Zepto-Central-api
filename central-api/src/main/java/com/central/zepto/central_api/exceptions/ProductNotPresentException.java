package com.central.zepto.central_api.exceptions;

public class ProductNotPresentException extends RuntimeException {
    public ProductNotPresentException(String message) {
        super(message);
    }
}

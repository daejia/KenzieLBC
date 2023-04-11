package com.kenzie.appserver.service;

public class CartNotFoundException extends Throwable{
    public CartNotFoundException(String message) {
        super(message);
    }
}

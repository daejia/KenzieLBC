package com.kenzie.appserver.service;

public class NoStoreFoundException extends RuntimeException{
    public NoStoreFoundException(String message) {
        super(message);
    }
}

package com.tyson.useless.system.exception;

public class BadGatewayException extends RuntimeException {

    public BadGatewayException(String message) {
        super(message);
        System.out.println("bad gateway exception was thrown");
    }
}

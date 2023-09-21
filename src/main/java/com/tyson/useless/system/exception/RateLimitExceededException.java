package com.tyson.useless.system.exception;
public class RateLimitExceededException extends RuntimeException {

    private int httpStatusCode;

    public RateLimitExceededException(String message, int httpStatusCode) {
        super(message);
        this.httpStatusCode = httpStatusCode;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }
}


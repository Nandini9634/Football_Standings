package com.footballstanding.footballstanding.exception;

public class ApiAccessException extends RuntimeException {

    public ApiAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}

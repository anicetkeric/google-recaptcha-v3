package com.rad.bootlabs.springreactiverecaptchav3.common.exception;

public class InvalidPasswordException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String message;
    public InvalidPasswordException(String message) {
        super(message);
    }
}

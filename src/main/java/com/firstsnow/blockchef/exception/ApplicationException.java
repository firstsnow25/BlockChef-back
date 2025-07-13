package com.firstsnow.blockchef.exception;

import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException{
    private final ApplicationError error;

    public ApplicationException(ApplicationError error) {
        super(error.getMessage());
        this.error = error;
    }

    public HttpStatus getStatus() {
        return error.getStatus();
    }

    public String getErrorCode() {
        return error.name();
    }

    public String getErrorMessage() {
        return error.getMessage();
    }
}

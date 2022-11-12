package com.example.restApi.Exception;

import org.springframework.http.HttpStatus;

public class JwtAuthenticationException extends Exception {

    private HttpStatus status;

    public JwtAuthenticationException(String message) {
        super(message);
    }

    public JwtAuthenticationException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}

package com.trilogyed.shippingedgeservice.controller;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class ClientErrorException extends Exception {
    private HttpStatus status;
    private List<String> messages;

    ClientErrorException(HttpStatus status, List<String> messages) {
        super(messages.toString());
        this.status = status;
        this.messages = messages;
    }

    ClientErrorException(HttpStatus status, String... messages) {
        this(status, Arrays.asList(messages));
    }

    public HttpStatus getStatus() {
        return status;
    }

    public List<String> getMessages() {
        return messages;
    }
}

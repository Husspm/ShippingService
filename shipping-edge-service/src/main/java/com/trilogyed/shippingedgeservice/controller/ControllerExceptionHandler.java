package com.trilogyed.shippingedgeservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(ClientErrorException.class)
    public ClientError handleClientException(ClientErrorException e, WebRequest req) {
        return new ClientError(e.getMessages(), e.getStatus(), req);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ClientError handleMethodArgumentNotValidException(MethodArgumentNotValidException e, WebRequest req) {
        List<String> errors = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());
        return new ClientError(errors, HttpStatus.UNPROCESSABLE_ENTITY, req);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ClientError handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, WebRequest req) {
        String param = e.getName();
        String type = e.getRequiredType().getSimpleName();
        return new ClientError(
                "Parameter `" + param + "` should be " +
                        (type.substring(0, 1).matches("[AEIOU]") ? "an " : "a ") + type,
                HttpStatus.BAD_REQUEST, req);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ClientError handleHttpMessageNotReadableException(HttpMessageNotReadableException e, WebRequest req) {
        String message = e.getMessage();
        if (message != null && message.contains("JSON parse error")) {
            Matcher fieldMatcher = Pattern.compile("through reference chain:.*\\[\"(.*?)\"]", Pattern.DOTALL)
                    .matcher(message);
            String error;
            HttpStatus status;
            if (fieldMatcher.find()) {
                error = fieldMatcher.group(1) + ": failed to parse value";
                status = HttpStatus.UNPROCESSABLE_ENTITY;
            } else {
                error = "Failed to parse JSON request body";
                status = HttpStatus.BAD_REQUEST;
            }
            return new ClientError(error, status, req);
        } else {
            e.printStackTrace();
            return new ClientError("HTTP message not readable", HttpStatus.BAD_REQUEST, req);
        }
    }
}

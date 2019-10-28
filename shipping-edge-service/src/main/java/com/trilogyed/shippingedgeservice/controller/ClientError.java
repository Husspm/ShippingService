package com.trilogyed.shippingedgeservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ClientError extends ResponseEntity<ClientError.Error> {
    static class Error {
        private String timestamp;
        private Integer status;
        private String reason;
        private List<String> errors;
        private String path;

        Error(List<String> errors, HttpStatus status, WebRequest req) {
            this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            this.status = status.value();
            this.errors = new ArrayList<>(errors);
            this.reason = status.getReasonPhrase();
            this.path = req.toString().replaceAll(".*uri=|;.*", "");
        }

        Error(String error, HttpStatus status, WebRequest req) {
            this(Collections.singletonList(error), status, req);
        }

        public String getTimestamp() {
            return timestamp;
        }

        public Integer getStatus() {
            return status;
        }

        public String getReason() {
            return reason;
        }

        public List<String> getErrors() {
            return errors;
        }

        public String getPath() {
            return path;
        }
    }

    ClientError(String error, HttpStatus status, WebRequest req) {
        super(new Error(error, status, req), status);
    }

    ClientError(List<String> errors, HttpStatus status, WebRequest req) {
        super(new Error(errors, status, req), status);
    }
}

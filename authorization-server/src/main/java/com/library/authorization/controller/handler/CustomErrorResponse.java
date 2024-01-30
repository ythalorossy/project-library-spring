package com.library.authorization.controller.handler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomErrorResponse {

    private String errorCode;
    private String message;
    private String timestamp;

    public CustomErrorResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
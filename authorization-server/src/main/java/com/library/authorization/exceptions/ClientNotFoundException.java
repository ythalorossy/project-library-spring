package com.library.authorization.exceptions;

import com.library.authorization.controller.handler.CustomError;

public class ClientNotFoundException extends RuntimeException {

    private final String errorCode;

    public ClientNotFoundException() {
        super(CustomError.CLIENT_NOT_FOUND.message());
        this.errorCode = CustomError.CLIENT_NOT_FOUND.code();
    }

    public String getErrorCode() {
        return errorCode;
    }
}

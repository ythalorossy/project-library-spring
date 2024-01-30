package com.library.authorization.controller.handler;

public enum CustomError {

    CLIENT_NOT_FOUND("CLINOTFOU", "Client not found");
    
    private String code;
    private String str;

    CustomError(String code, String message) {
        this.code = code;
        str = message;
    }

    public String code() {
        return this.code;
    }

    public String message() {
        return this.str;
    }
}
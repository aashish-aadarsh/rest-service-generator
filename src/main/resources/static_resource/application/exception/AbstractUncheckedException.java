package com.devop.aashish.java.myapplication.application.exception;

public abstract class AbstractUncheckedException extends RuntimeException implements BaseException {
    private String code;
    private Integer status;
    private String errorMessage;

    AbstractUncheckedException(final String code, final String message, final Integer status) {
        super(message);
        this.code = code;
        this.status = status;
        this.errorMessage = message;
    }


    public String getCode() {
        return this.code;
    }

    public Integer getStatus() {
        return this.status;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}

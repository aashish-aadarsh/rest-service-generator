package com.devop.aashish.java.myapplication.application.exception;

import com.devop.aashish.java.myapplication.application.constant.ErrorCodes;
import org.springframework.http.HttpStatus;

public class UnAuthenticatedException extends AbstractUncheckedException {

    public UnAuthenticatedException(String message) {
        super(ErrorCodes.ERROR_UNAUTHENTICATED, message, HttpStatus.UNAUTHORIZED.value());

    }
}

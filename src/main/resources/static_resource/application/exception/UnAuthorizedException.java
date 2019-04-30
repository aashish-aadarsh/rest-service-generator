package com.devop.aashish.java.myapplication.application.exception;

import com.devop.aashish.java.myapplication.application.constant.ErrorCodes;
import org.springframework.http.HttpStatus;

public class UnAuthorizedException extends AbstractUncheckedException {

    public UnAuthorizedException(String message) {
        super(ErrorCodes.ERROR_UNAUTHORIZED, message, HttpStatus.FORBIDDEN.value());

    }
}

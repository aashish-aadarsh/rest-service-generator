package com.devop.aashish.java.myapplication.application.exception;

import com.devop.aashish.java.myapplication.application.constant.ErrorCodes;
import org.springframework.http.HttpStatus;

public class InvalidInputException extends AbstractUncheckedException {

    public InvalidInputException(String message) {
        super(ErrorCodes.ERROR_INVALID_INPUT, message, HttpStatus.BAD_REQUEST.value());
    }
}

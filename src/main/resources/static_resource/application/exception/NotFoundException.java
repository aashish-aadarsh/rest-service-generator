package com.devop.aashish.java.myapplication.application.exception;

import com.devop.aashish.java.myapplication.application.constant.ErrorCodes;
import org.springframework.http.HttpStatus;

public class NotFoundException extends AbstractUncheckedException {

    protected NotFoundException(String message) {
        super(ErrorCodes.ERROR_NOT_FOUND, message, HttpStatus.NOT_FOUND.value());

    }
}

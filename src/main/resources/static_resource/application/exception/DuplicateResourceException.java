package com.devop.aashish.java.myapplication.application.exception;

import com.devop.aashish.java.myapplication.application.constant.ErrorCodes;
import org.springframework.http.HttpStatus;

public class DuplicateResourceException extends AbstractUncheckedException {

    protected DuplicateResourceException(final String message) {
        super(ErrorCodes.ERROR_DUPLICATE_RESOURCE, message, HttpStatus.CONFLICT.value());
    }
}

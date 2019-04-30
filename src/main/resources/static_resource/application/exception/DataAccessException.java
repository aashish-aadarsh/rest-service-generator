package com.devop.aashish.java.myapplication.application.exception;

import com.devop.aashish.java.myapplication.application.constant.ErrorCodes;
import org.springframework.http.HttpStatus;

public class DataAccessException extends AbstractUncheckedException {

    protected DataAccessException(String message) {
        super(ErrorCodes.ERROR_DATA_ACCESS, message, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}

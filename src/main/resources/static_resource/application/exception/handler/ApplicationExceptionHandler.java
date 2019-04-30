package com.devop.aashish.java.myapplication.application.exception.handler;

import com.devop.aashish.java.myapplication.application.constant.ErrorCodes;
import com.devop.aashish.java.myapplication.application.exception.AbstractUncheckedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

@ControllerAdvice
public class ApplicationExceptionHandler {


    @ExceptionHandler(Throwable.class)
    public final ResponseEntity<ErrorResponse> handleException(Exception ex) throws Exception {
        ErrorResponse.ErrorResponseBuilder builder = new ErrorResponse.ErrorResponseBuilder();
        if (ex instanceof AbstractUncheckedException) {
            AbstractUncheckedException exception = (AbstractUncheckedException) ex;
            builder.code(exception.getCode());
            builder.errorMessage(exception.getErrorMessage());
            builder.status(exception.getStatus());
        } else if (ex instanceof AuthenticationException) {
            builder.code(ErrorCodes.ERROR_UNAUTHENTICATED);
            builder.errorMessage(ex.getMessage());
            builder.status(HttpStatus.UNAUTHORIZED.value());

        } else if (ex instanceof MethodArgumentNotValidException) {
            builder.code(ErrorCodes.ERROR_INVALID_INPUT);
            builder.errorMessage(getArgsError((MethodArgumentNotValidException) ex));
            builder.status(HttpStatus.BAD_REQUEST.value());
        } else {
            builder.code(ErrorCodes.ERROR_UNKNOWN);
            builder.errorMessage(ex.getMessage());
            builder.status(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        builder.timestamp(new Date());
        ErrorResponse response = builder.build();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    private String getArgsError(MethodArgumentNotValidException ex) {
        StringJoiner formattedString = new StringJoiner("\n");
        List<ObjectError> bindingResult = ex.getBindingResult().getAllErrors();
        bindingResult.forEach(objectError ->
                formattedString.add(objectError.getDefaultMessage())
        );
        return formattedString.toString();
    }
}

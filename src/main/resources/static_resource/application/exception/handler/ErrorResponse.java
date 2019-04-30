package com.devop.aashish.java.myapplication.application.exception.handler;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
class ErrorResponse {
    private Date timestamp;
    private String errorMessage;
    private String code;
    private Integer status;

    @Builder
    public ErrorResponse(Date timestamp, String errorMessage, String code, Integer status) {
        this.timestamp = timestamp;
        this.errorMessage = errorMessage;
        this.code = code;
        this.status = status;
    }
}

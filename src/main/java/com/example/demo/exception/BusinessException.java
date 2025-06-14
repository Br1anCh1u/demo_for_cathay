package com.example.demo.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException{

    private String errorCode;
    private Object[] args;

    public BusinessException(String errorCode, Object... args) {
        this.errorCode = errorCode;
        this.args = args;
    }

}

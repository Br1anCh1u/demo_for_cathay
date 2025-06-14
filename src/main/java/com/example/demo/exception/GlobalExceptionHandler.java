package com.example.demo.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleBusinessException(BusinessException ex) {
        String message = messageSource.getMessage(ex.getErrorCode(), ex.getArgs(), "", LocaleContextHolder.getLocale());

        Map<String, Object> response = new HashMap<>();
        response.put("errorCode", ex.getErrorCode());
        response.put("message", message);
        response.put("timestamp", LocalDateTime.now());
        return response;
    }

    @ExceptionHandler(BusinessNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleBusinessNotFoundException(BusinessNotFoundException ex) {
    }

}
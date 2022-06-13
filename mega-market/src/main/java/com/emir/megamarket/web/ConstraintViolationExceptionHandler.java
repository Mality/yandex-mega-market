package com.emir.megamarket.web;

import com.emir.megamarket.web.error.Error;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ConstraintViolationExceptionHandler {

    @ExceptionHandler(value = {ConstraintViolationException.class, MethodArgumentNotValidException.class})
    protected ResponseEntity<Object> handleConstraintViolation(Exception ex, WebRequest request) {
        return ResponseEntity.status(200).body(new Error(200, "Validation Failed"));
    }
}

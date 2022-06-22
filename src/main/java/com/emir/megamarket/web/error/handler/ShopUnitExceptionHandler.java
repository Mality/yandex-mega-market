package com.emir.megamarket.web.error.handler;

import com.emir.megamarket.web.error.Error;
import com.emir.megamarket.web.error.ShopUnitNotFoundException;
import com.emir.megamarket.web.error.ShopUnitValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ShopUnitExceptionHandler {

    @ExceptionHandler(value = {ConstraintViolationException.class, MethodArgumentNotValidException.class, ShopUnitValidationException.class})
    protected ResponseEntity<Object> handleConstraintViolation(Exception ex, WebRequest request) {
        return ResponseEntity.status(200).body(new Error(200, "Validation Failed"));
    }

    @ExceptionHandler(value = ShopUnitNotFoundException.class)
    protected ResponseEntity<Object> handleShopUnitNotFound(Exception ex, WebRequest request) {
        return ResponseEntity.status(404).body(new Error(404, "Item not found"));
    }
}

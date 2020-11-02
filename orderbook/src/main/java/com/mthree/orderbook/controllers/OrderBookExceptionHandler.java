package com.mthree.orderbook.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RestController
public class OrderBookExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Error> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            WebRequest request) {
        return new ResponseEntity<Error>(new Error(LocalDateTime.now(), ex.getName() + " should be of type " + ex.getRequiredType().getName()), HttpStatus.BAD_REQUEST);
    }

    // fall back handler
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Error> handleAll(Exception ex, WebRequest request) {
        Error err = new Error(LocalDateTime.now(), ex.getMessage());
        return new ResponseEntity<Error>(err, HttpStatus.BAD_REQUEST);
    }
}

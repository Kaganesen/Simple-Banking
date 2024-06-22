package com.eteration.simplebanking.exception;

import com.eteration.simplebanking.common.util.result.ErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BankAccountNotFoundException.class)
    public ResponseEntity<Object> handleBankAccountNotFoundException(BankAccountNotFoundException exception) {
        ErrorResult errorResponse = new ErrorResult(exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}

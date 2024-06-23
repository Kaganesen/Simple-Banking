package com.eteration.simplebanking.exception;

public class InvalidTransactionException extends RuntimeException{

    public InvalidTransactionException(String message) {
        super(message);
    }
}

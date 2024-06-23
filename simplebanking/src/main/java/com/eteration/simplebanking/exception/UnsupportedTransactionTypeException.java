package com.eteration.simplebanking.exception;

public class UnsupportedTransactionTypeException extends RuntimeException{

    public UnsupportedTransactionTypeException(String message) {
        super(message);
    }

}

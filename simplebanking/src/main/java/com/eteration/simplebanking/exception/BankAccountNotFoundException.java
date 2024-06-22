package com.eteration.simplebanking.exception;

public class BankAccountNotFoundException extends RuntimeException {

    public BankAccountNotFoundException(String message) {
        super(message);
    }
}

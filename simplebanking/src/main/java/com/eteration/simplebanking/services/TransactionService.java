package com.eteration.simplebanking.services;

import com.eteration.simplebanking.model.entity.BankAccount;
import com.eteration.simplebanking.model.entity.Transaction;

public interface TransactionService {

    Transaction deposit(BankAccount bankAccount, double amount);

    Transaction withdraw(BankAccount bankAccount, double amount);

    Transaction payment(BankAccount bankAccount, double amount);
}

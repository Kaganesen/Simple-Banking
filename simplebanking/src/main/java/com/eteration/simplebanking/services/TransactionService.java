package com.eteration.simplebanking.services;

import com.eteration.simplebanking.model.entity.BankAccount;
import com.eteration.simplebanking.model.entity.Transaction;

public interface TransactionService {

    Transaction deposit(BankAccount bankAccount, double amount);

    Transaction withdraw(BankAccount bankAccount, double amount);

    Transaction billPayment(BankAccount bankAccount, double amount);
}

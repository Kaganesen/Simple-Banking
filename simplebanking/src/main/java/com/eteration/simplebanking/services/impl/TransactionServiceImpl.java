package com.eteration.simplebanking.services.impl;

import com.eteration.simplebanking.model.entity.BankAccount;
import com.eteration.simplebanking.model.entity.Transaction;
import com.eteration.simplebanking.model.enums.TransactionType;
import com.eteration.simplebanking.services.TransactionService;
import com.eteration.simplebanking.helpers.TransactionServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {


    private final TransactionServiceHelper transactionServiceHelper;


    @Override
    public Transaction deposit(BankAccount bankAccount, double amount) {
        return transactionServiceHelper.processTransaction(bankAccount, amount, TransactionType.DEPOSIT);
    }

    @Override
    public Transaction withdraw(BankAccount bankAccount, double amount) {
        return transactionServiceHelper.processTransaction(bankAccount, amount, TransactionType.WITHDRAW);
    }

    @Override
    public Transaction billPayment(BankAccount bankAccount, double amount) {
        return transactionServiceHelper.processTransaction(bankAccount, amount, TransactionType.BILL_PAYMENT);
    }

}

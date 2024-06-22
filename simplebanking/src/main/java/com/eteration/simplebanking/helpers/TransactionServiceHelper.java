package com.eteration.simplebanking.helpers;

import com.eteration.simplebanking.common.util.factory.TransactionFactory;
import com.eteration.simplebanking.model.entity.BankAccount;
import com.eteration.simplebanking.model.entity.Transaction;
import com.eteration.simplebanking.model.enums.TransactionType;
import com.eteration.simplebanking.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TransactionServiceHelper {

    private final TransactionRepository transactionRepository;

    private final TransactionFactory transactionFactory;


    public Transaction processTransaction(BankAccount bankAccount, double amount, TransactionType transactionType) {
        String approvalCode = UUID.randomUUID().toString();

        Transaction transaction = transactionFactory.createTransaction(transactionType, amount);
        transaction.setBankAccount(bankAccount);
        transaction.setApprovalCode(approvalCode);
        transaction.setAmount(amount);
        transaction.setTransactionType(transactionType);
        transactionRepository.save(transaction);

        return transaction;
    }

}

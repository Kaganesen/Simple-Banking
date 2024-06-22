package com.eteration.simplebanking.services.impl;

import com.eteration.simplebanking.helpers.TransactionServiceHelper;
import com.eteration.simplebanking.model.entity.*;
import com.eteration.simplebanking.model.enums.TransactionType;
import com.eteration.simplebanking.services.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class TransactionServiceImplTest {

    @Mock
    private TransactionServiceHelper transactionServiceHelper;

    @InjectMocks
    private TransactionServiceImpl transactionServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deposit() {
        // Mock veri hazırlığı
        BankAccount bankAccount = new BankAccount();
        double amount = 1000.0;
        DepositTransaction expectedTransaction = new DepositTransaction(amount);

        // Mock davranışı
        when(transactionServiceHelper.processTransaction(any(BankAccount.class), eq(amount), eq(TransactionType.DEPOSIT)))
                .thenReturn(expectedTransaction);

        // Metod çağrısı
        Transaction result = transactionServiceImpl.deposit(bankAccount, amount);

        // Doğrulamalar
        assertNotNull(result);
        assertEquals(expectedTransaction, result);
        verify(transactionServiceHelper, times(1))
                .processTransaction(bankAccount, amount, TransactionType.DEPOSIT);
    }

    @Test
    void withdraw() {
        BankAccount bankAccount = new BankAccount();
        double amount = 1000.0;
        WithdrawalTransaction expectedTransaction = new WithdrawalTransaction(amount);

        when(transactionServiceHelper.processTransaction(any(BankAccount.class), eq(amount), eq(TransactionType.WITHDRAW)))
                .thenReturn(expectedTransaction);

        Transaction result = transactionServiceImpl.withdraw(bankAccount, amount);

        assertNotNull(result);
        assertEquals(expectedTransaction, result);
        verify(transactionServiceHelper, times(1))
                .processTransaction(bankAccount, amount, TransactionType.WITHDRAW);
    }

    @Test
    void payment() {
        BankAccount bankAccount = new BankAccount();
        double amount = 2000.0;
        BillPaymentTransaction expectedTransaction = new BillPaymentTransaction(amount);

        when(transactionServiceHelper.processTransaction(any(BankAccount.class), eq(amount), eq(TransactionType.BILL_PAYMENT)))
                .thenReturn(expectedTransaction);

        Transaction result = transactionServiceImpl.payment(bankAccount, amount);

        assertNotNull(result);
        assertEquals(expectedTransaction, result);
        verify(transactionServiceHelper, times(1))
                .processTransaction(bankAccount, amount, TransactionType.BILL_PAYMENT);

    }
}
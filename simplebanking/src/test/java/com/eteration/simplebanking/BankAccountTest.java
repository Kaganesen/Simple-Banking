package com.eteration.simplebanking;


import com.eteration.simplebanking.exception.InvalidTransactionException;
import com.eteration.simplebanking.model.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class BankAccountTest {

    private BankAccount bankAccount;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bankAccount = new BankAccount("123", "Kagan Esen");
    }

    @Test
    void post_ShouldAddTransactionToBankAccount() {
        Transaction transaction = new DepositTransaction(100.0);

        bankAccount.post(transaction);

        assertEquals(1, bankAccount.getTransactions().size());
        assertTrue(bankAccount.getTransactions().contains(transaction));
        assertEquals(bankAccount, transaction.getBankAccount());
    }

    @Test
    void post_ShouldSetTransactionDateToCurrentDate() {
        Transaction transaction = new WithdrawalTransaction(50.0);

        bankAccount.post(transaction);

        LocalDateTime currentDateTime = LocalDateTime.now();
        assertTrue(transaction.getDate().isBefore(currentDateTime.plusSeconds(1)));
        assertTrue(transaction.getDate().isAfter(currentDateTime.minusSeconds(1)));
    }

    @Test
    void post_ShouldThrowException_WhenTransactionIsNull() {
        Transaction transaction = null;

        assertThrows(InvalidTransactionException.class, () -> bankAccount.post(transaction));
    }

    @Test
    void post_ShouldAddMultipleTransactionsInOrder() {
        Transaction depositTransaction = new DepositTransaction(200.0);
        Transaction withdrawalTransaction = new WithdrawalTransaction(100.0);
        Transaction billPaymentTransaction = new BillPaymentTransaction(300.0);

        bankAccount.post(depositTransaction);
        bankAccount.post(withdrawalTransaction);
        bankAccount.post(billPaymentTransaction);

        assertEquals(3, bankAccount.getTransactions().size());
        assertEquals(depositTransaction, bankAccount.getTransactions().get(0));
        assertEquals(withdrawalTransaction, bankAccount.getTransactions().get(1));
        assertEquals(billPaymentTransaction, bankAccount.getTransactions().get(2));
    }


}

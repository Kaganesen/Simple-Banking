package com.eteration.simplebanking;


import com.eteration.simplebanking.model.entity.BankAccount;
import com.eteration.simplebanking.model.entity.DepositTransaction;
import com.eteration.simplebanking.model.entity.Transaction;
import com.eteration.simplebanking.model.entity.WithdrawalTransaction;
import com.eteration.simplebanking.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BankAccountTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private BankAccount bankAccount;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bankAccount = new BankAccount("12345", "Kagan Esen");
        bankAccount.setBalance(1000.0);
        bankAccount.setTransactions(new ArrayList<>());
    }

    @Test
    public void testCreateAccountAndInitialBalance() {
        assertEquals("12345", bankAccount.getAccountNumber());
        assertEquals("Kagan Esen", bankAccount.getOwner());
        assertEquals(1000, bankAccount.getBalance());
        assertTrue(bankAccount.getTransactions().isEmpty());
    }


    @Test
    public void deposit_ValidAmount_ShouldIncreaseBalanceAndAddTransaction() {
        double amount = 500.0;

        bankAccount.deposit(amount);

        assertEquals(1500.0, bankAccount.getBalance());
        assertEquals(1, bankAccount.getTransactions().size());
        Transaction transaction = bankAccount.getTransactions().get(0);
        assertTrue(transaction instanceof DepositTransaction);
        assertEquals(amount, transaction.getAmount());

        // Verify that transactionRepository.save was called once with any DepositTransaction
        verify(transactionRepository, times(1)).save(any(DepositTransaction.class));
    }


    @Test
    public void withdraw_ValidAmount_ShouldDecreaseBalanceAndAddTransaction() {
        double amount = 500.0;

        bankAccount.withdraw(amount);

        assertEquals(500.0, bankAccount.getBalance());
        assertEquals(1, bankAccount.getTransactions().size());
        assertTrue(bankAccount.getTransactions().get(0) instanceof WithdrawalTransaction);
        assertEquals(amount, bankAccount.getTransactions().get(0).getAmount());

        // Verify that transactionRepository.save was called once with any WithdrawalTransaction
        verify(transactionRepository, times(1)).save(any(WithdrawalTransaction.class));
    }

}

package com.eteration.simplebanking.helpers;

import com.eteration.simplebanking.common.util.factory.TransactionFactory;
import com.eteration.simplebanking.common.util.messages.Messages;
import com.eteration.simplebanking.exception.UnsupportedTransactionTypeException;
import com.eteration.simplebanking.model.entity.*;
import com.eteration.simplebanking.model.enums.TransactionType;
import com.eteration.simplebanking.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceHelperTest {

    @InjectMocks
    private TransactionServiceHelper transactionServiceHelper;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionFactory transactionFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void processTransaction_ShouldCreateAndSaveDepositTransaction() {
        testTransactionProcessing(TransactionType.DEPOSIT);
    }

    @Test
    void processTransaction_ShouldCreateAndSaveWithdrawTransaction() {
        testTransactionProcessing(TransactionType.WITHDRAW);
    }

    @Test
    void processTransaction_ShouldCreateAndSaveBillBillPaymentTransaction() {
        testTransactionProcessing(TransactionType.BILL_PAYMENT);
    }

    private void testTransactionProcessing(TransactionType transactionType) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountNumber("12345");

        double amount = 100.0;

        Transaction transaction;
        if (transactionType == TransactionType.DEPOSIT) {
            transaction = new DepositTransaction();
        } else if (transactionType == TransactionType.WITHDRAW) {
            transaction = new WithdrawalTransaction();
        } else if (transactionType == TransactionType.BILL_PAYMENT) {
            transaction = new BillPaymentTransaction();
        } else {
            throw new UnsupportedTransactionTypeException(String.format("%s: %s", Messages.UNSUPPORTED_TRANSACTION_TYPE, transactionType));
        }

        transaction.setBankAccount(bankAccount);
        transaction.setAmount(amount);
        transaction.setTransactionType(transactionType);

        when(transactionFactory.createTransaction(transactionType, amount)).thenReturn(transaction);

        Transaction result = transactionServiceHelper.processTransaction(bankAccount, amount, transactionType);

        assertNotNull(result);
        assertEquals(bankAccount, result.getBankAccount());
        assertEquals(amount, result.getAmount());
        assertEquals(transactionType, result.getTransactionType());

        assertNotNull(result.getApprovalCode());
        assertTrue(UUID.fromString(result.getApprovalCode()) instanceof UUID);

        ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionRepository, times(1)).save(transactionCaptor.capture());
        Transaction savedTransaction = transactionCaptor.getValue();
        assertEquals(transaction, savedTransaction);
    }
}
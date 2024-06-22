package com.eteration.simplebanking.services.impl;

import com.eteration.simplebanking.common.util.mapper.AccountMapper;

import com.eteration.simplebanking.common.util.messages.Messages;
import com.eteration.simplebanking.common.util.result.DataResult;
import com.eteration.simplebanking.common.util.result.Result;
import com.eteration.simplebanking.exception.BankAccountNotFoundException;
import com.eteration.simplebanking.model.dto.request.CreateAccountRequest;
import com.eteration.simplebanking.model.dto.request.CreditRequest;
import com.eteration.simplebanking.model.dto.request.DebitRequest;
import com.eteration.simplebanking.model.dto.request.PaymentRequest;
import com.eteration.simplebanking.model.dto.response.*;
import com.eteration.simplebanking.model.entity.*;
import com.eteration.simplebanking.repository.BankAccountRepository;
import com.eteration.simplebanking.services.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class BankAccountServiceImplTest {

    @InjectMocks
    private BankAccountServiceImpl bankAccountServiceImpl;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private TransactionService transactionService;

    @Mock
    private AccountMapper accountMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAccount() {

        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        BankAccount bankAccount = new BankAccount();
        CreateAccountResponse createAccountResponse = new CreateAccountResponse();

        when(accountMapper.createAccountRequestToBankAccount(createAccountRequest)).thenReturn(bankAccount);
        when(bankAccountRepository.save(bankAccount)).thenReturn(bankAccount);
        when(accountMapper.bankAccountToCreateAccountResponse(bankAccount)).thenReturn(createAccountResponse);

        DataResult<CreateAccountResponse> result = bankAccountServiceImpl.createAccount(createAccountRequest);


        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals(Messages.ACCOUNT_CREATED_SUCCESSFULLY, result.getMessage());
        assertEquals(createAccountResponse, result.getData());
    }

    @Test
    public void testCredit() {
        double amount = 100.0;
        CreditRequest creditRequest = new CreditRequest("444", amount);
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountNumber("123456");
        bankAccount.setBalance(500.0);

        Transaction transaction = new DepositTransaction(amount);
        transaction.setApprovalCode("111222333");

        CreditResponse creditResponse = new CreditResponse();

        when(bankAccountRepository.findByAccountNumber("444")).thenReturn(Optional.of(bankAccount));
        when(transactionService.deposit(bankAccount, 100.0)).thenReturn(transaction);
        when(accountMapper.createBankAccountToCreateCreditResponse(bankAccount)).thenReturn(creditResponse);

        DataResult<CreditResponse> result = bankAccountServiceImpl.credit(creditRequest);

        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals(Messages.CREDIT_SUCCESSFULLY, result.getMessage());
        assertEquals(creditResponse, result.getData());
        assertEquals("111222333", result.getData().getApprovalCode());
    }

    @Test
    public void testDebit() {
        double amount = 50;
        DebitRequest debitRequest = new DebitRequest("222333", amount);
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountNumber("222333");
        bankAccount.setBalance(100.0);

        Transaction transaction = new WithdrawalTransaction(amount);
        transaction.setApprovalCode("333444");

        DebitResponse debitResponse = new DebitResponse();

        when(bankAccountRepository.findByAccountNumber("222333")).thenReturn(Optional.of(bankAccount));
        when(transactionService.withdraw(bankAccount, 50.0)).thenReturn(transaction);
        when(accountMapper.createBankAccountToCreateDebitResponse(bankAccount)).thenReturn(debitResponse);

        DataResult<DebitResponse> result = bankAccountServiceImpl.debit(debitRequest);

        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals(Messages.DEBIT_SUCCESSFULLY, result.getMessage());
        assertEquals(debitResponse, result.getData());
        assertEquals("333444", result.getData().getApprovalCode());
    }

    @Test
    public void testPayment() {
        double amount = 25.30;
        PaymentRequest paymentRequest = new PaymentRequest("1111", amount);
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountNumber("123456");
        bankAccount.setBalance(75.0);

        Transaction transaction = new BillPaymentTransaction(amount);
        transaction.setApprovalCode("555");

        PaymentResponse paymentResponse = new PaymentResponse();

        when(bankAccountRepository.findByAccountNumber("1111")).thenReturn(Optional.of(bankAccount));
        when(transactionService.payment(bankAccount, amount)).thenReturn(transaction);
        when(accountMapper.createBankAccountToCreatePaymentResponse(bankAccount)).thenReturn(paymentResponse);

        DataResult<PaymentResponse> result = bankAccountServiceImpl.payment(paymentRequest);

        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals(Messages.PAYMENT_SUCCESSFULLY, result.getMessage());
        assertEquals(paymentResponse, result.getData());
        assertEquals("555", result.getData().getApprovalCode());
    }

    @Test
    public void testGetByAccountNumber() {
        String accountNumber = "12345";
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountNumber(accountNumber);

        AccountDto accountDto = new AccountDto();

        when(bankAccountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(bankAccount));
        when(accountMapper.accountDtoToBankAccount(bankAccount)).thenReturn(accountDto);

        DataResult<AccountDto> result = bankAccountServiceImpl.getByAccountNumber(accountNumber);

        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals(Messages.ACCOUNT_FOUND, result.getMessage());
        assertEquals(accountDto, result.getData());
    }

    @Test
    public void testDeleteAccount() {
        String accountNumber = "123";
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountNumber(accountNumber);

        when(bankAccountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(bankAccount));
        doNothing().when(bankAccountRepository).delete(bankAccount);

        Result result = bankAccountServiceImpl.deleteAccount(accountNumber);

        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals(String.format("%s: %s", Messages.ACCOUNT_DELETED_SUCCESSFULLY, accountNumber), result.getMessage());
    }

    @Test
    public void givenExistingAccountNumber_whenGetBankAccount_thenReturnBankAccount() {
        String accountNumber = "112233";
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountNumber(accountNumber);

        Mockito.when(bankAccountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(bankAccount));

        BankAccount result = bankAccountServiceImpl.getBankAccount(accountNumber);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(accountNumber, result.getAccountNumber());
    }

    @Test
    public void givenNonExistingAccountNumber_whenGetBankAccount_thenThrowException() {
        String accountNumber = "112233";

        Mockito.when(bankAccountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.empty());

        BankAccountNotFoundException exception = Assertions.assertThrows(
                BankAccountNotFoundException.class,
                () -> bankAccountServiceImpl.getBankAccount(accountNumber)
        );

        Assertions.assertEquals(String.format("%s: %s", Messages.BANK_ACCOUNT_NOT_FOUND, accountNumber), exception.getMessage());
    }
}
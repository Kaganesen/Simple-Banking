package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.common.util.messages.Messages;
import com.eteration.simplebanking.common.util.result.DataResult;
import com.eteration.simplebanking.common.util.result.Result;
import com.eteration.simplebanking.model.dto.request.CreateAccountRequest;
import com.eteration.simplebanking.model.dto.request.CreditRequest;
import com.eteration.simplebanking.model.dto.request.DebitRequest;
import com.eteration.simplebanking.model.dto.request.BillPaymentRequest;
import com.eteration.simplebanking.model.dto.response.*;
import com.eteration.simplebanking.services.BankAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class BankAccountControllerTest {

    @Mock
    private BankAccountService bankAccountService;

    @InjectMocks
    private BankAccountController bankAccountController;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAccount() {
        String ownerName = "Kagan";
        CreateAccountRequest request = new CreateAccountRequest();
        request.setOwner(ownerName);

        LocalDateTime now = LocalDateTime.now();

        CreateAccountResponse expectedResponse = new CreateAccountResponse();
        expectedResponse.setAccountNumber("123");
        expectedResponse.setOwner(ownerName);
        expectedResponse.setCreateDate(now);

        DataResult<CreateAccountResponse> expectedResult = new DataResult<>(expectedResponse, true, Messages.ACCOUNT_CREATED_SUCCESSFULLY);

        when(bankAccountService.createAccount(any(CreateAccountRequest.class))).thenReturn(expectedResult);

        DataResult<CreateAccountResponse> actualResult = bankAccountController.createAccount(request);

        assertTrue(actualResult.isSuccess());

        CreateAccountResponse actualResponse = actualResult.getData();
        assertEquals(expectedResponse.getAccountNumber(), actualResponse.getAccountNumber());
        assertEquals(expectedResponse.getOwner(), actualResponse.getOwner());
        assertEquals(expectedResponse.getCreateDate(), actualResponse.getCreateDate());

        assertEquals(Messages.ACCOUNT_CREATED_SUCCESSFULLY, actualResult.getMessage());
    }

    @Test
    void testCredit() {
        CreditRequest request = new CreditRequest();
        CreditResponse expectedResponse = new CreditResponse();
        DataResult<CreditResponse> expectedResult = new DataResult<>(expectedResponse, true, Messages.CREDIT_SUCCESSFULLY);

        when(bankAccountService.credit(any(CreditRequest.class))).thenReturn(expectedResult);

        DataResult<CreditResponse> actualResult = bankAccountController.credit(request);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testDebit() {
        DebitRequest request = new DebitRequest();
        DebitResponse expectedResponse = new DebitResponse(); // Örnek bir DebitResponse oluşturabilirsiniz.
        DataResult<DebitResponse> expectedResult = new DataResult<>(expectedResponse, true, Messages.DEBIT_SUCCESSFULLY);

        when(bankAccountService.debit(request)).thenReturn(expectedResult);

        DataResult<DebitResponse> actualResult = bankAccountController.debit(request);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testPayment() {
        BillPaymentRequest request = new BillPaymentRequest();
        PaymentResponse expectedResponse = new PaymentResponse();
        DataResult<PaymentResponse> expectedResult = new DataResult<>(expectedResponse, true, Messages.PAYMENT_SUCCESSFULLY);

        when(bankAccountService.billPayment(request)).thenReturn(expectedResult);

        DataResult<PaymentResponse> actualResult = bankAccountController.billPayment(request);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetByAccountNumber() {
        String accountNumber = "123";
        AccountDto expectedDto = new AccountDto();
        DataResult<AccountDto> expectedResult = new DataResult<>(expectedDto, true, Messages.ACCOUNT_FOUND);

        when(bankAccountService.getByAccountNumber(anyString())).thenReturn(expectedResult);

        DataResult<AccountDto> actualResult = bankAccountController.getByAccountNumber(accountNumber);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testDeleteAccount() {
        String accountNumber = "123";
        Result expectedResult = new Result(true);

        when(bankAccountService.deleteAccount(anyString())).thenReturn(expectedResult);

        Result actualResult = bankAccountController.deleteAccount(accountNumber);

        assertEquals(expectedResult, actualResult);
    }

}
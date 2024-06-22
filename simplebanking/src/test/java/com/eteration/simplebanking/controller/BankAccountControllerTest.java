package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.common.util.messages.Messages;
import com.eteration.simplebanking.common.util.result.DataResult;
import com.eteration.simplebanking.common.util.result.Result;
import com.eteration.simplebanking.model.dto.request.CreateAccountRequest;
import com.eteration.simplebanking.model.dto.request.CreditRequest;
import com.eteration.simplebanking.model.dto.request.DebitRequest;
import com.eteration.simplebanking.model.dto.request.PaymentRequest;
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
    void createAccount() {
        CreateAccountRequest request = new CreateAccountRequest();
        request.setOwner("Kagan");

        LocalDateTime now = LocalDateTime.now();

        CreateAccountResponse expectedResponse = new CreateAccountResponse();
        expectedResponse.setAccountNumber("123");
        expectedResponse.setOwner("Kagan");
        expectedResponse.setCreateDate(now);

        DataResult<CreateAccountResponse> expectedResult = new DataResult<>(expectedResponse, true, Messages.ACCOUNT_CREATED_SUCCESSFULLY);

        when(bankAccountService.createAccount(any(CreateAccountRequest.class))).thenReturn(expectedResult);

        DataResult<CreateAccountResponse> actualResult = bankAccountController.createAccount(request);

           assertTrue(actualResult.isSuccess());
        assertEquals(expectedResult.getData().getAccountNumber(), actualResult.getData().getAccountNumber());
        assertEquals(expectedResult.getData().getOwner(), actualResult.getData().getOwner());
        assertEquals(expectedResult.getData().getCreateDate(), actualResult.getData().getCreateDate());

        assertEquals(Messages.ACCOUNT_CREATED_SUCCESSFULLY, actualResult.getMessage());
    }

    @Test
    void credit() {

        CreditRequest request = new CreditRequest();
        CreditResponse response = new CreditResponse();
        DataResult<CreditResponse> result = new DataResult<>(response, true, Messages.CREDIT_SUCCESSFULLY);

        when(bankAccountService.credit(any(CreditRequest.class))).thenReturn(result);
        DataResult<CreditResponse> actualResult = bankAccountController.credit(request);

        assertEquals(result, actualResult);
    }

    @Test
    void debit() {
        DebitResponse createDebitResponse = new DebitResponse();
        DebitRequest createDebitRequest = new DebitRequest();
        DataResult<DebitResponse> result = new DataResult<>(createDebitResponse, true, Messages.DEBIT_SUCCESSFULLY);

        when(bankAccountService.debit(createDebitRequest)).thenReturn(result);
        DataResult<DebitResponse> actualResult = bankAccountController.debit(createDebitRequest);

        assertEquals(result, actualResult);
    }

    @Test
    void payment() {
        PaymentResponse createPaymentResponse = new PaymentResponse();
        PaymentRequest createPaymentRequest = new PaymentRequest();
        DataResult<PaymentResponse> result = new DataResult<>(createPaymentResponse, true, Messages.PAYMENT_SUCCESSFULLY);

        when(bankAccountService.payment(createPaymentRequest)).thenReturn(result);
        DataResult<PaymentResponse> actualResult = bankAccountController.payment(createPaymentRequest);

        assertEquals(result, actualResult);
    }

    @Test
    void getByAccountNumber() {
        AccountDto accountDto = new AccountDto();
        DataResult<AccountDto> result = new DataResult<>(accountDto, true, Messages.ACCOUNT_FOUND);

        when(bankAccountService.getByAccountNumber(anyString())).thenReturn(result);
        DataResult<AccountDto> actualResult = bankAccountController.getByAccountNumber(anyString());

        assertEquals(result, actualResult);
    }

    @Test
    void deleteAccount() {
        Result result = new Result(true);

        when(bankAccountService.deleteAccount(anyString())).thenReturn(result);
        Result actualResult = bankAccountController.deleteAccount(anyString());

        assertEquals(result, actualResult);
    }

}
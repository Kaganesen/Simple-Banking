package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.common.util.mapper.AccountMapper;
import com.eteration.simplebanking.common.util.result.DataResult;
import com.eteration.simplebanking.common.util.result.SuccessDataResult;
import com.eteration.simplebanking.model.dto.request.CreateAccountRequest;
import com.eteration.simplebanking.model.dto.response.CreateAccountResponse;
import com.eteration.simplebanking.services.BankAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class BankAccountControllerTest {

    @Mock
    private BankAccountService bankAccountService;

    @InjectMocks
    private BankAccountController bankAccountController;

    @Mock
    private AccountMapper accountMapper;

    private CreateAccountRequest createAccountRequest;
    private CreateAccountResponse createAccountResponse;
    private DataResult<CreateAccountResponse> serviceResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setOwner("Kagan Esen");

        createAccountResponse = new CreateAccountResponse();
        createAccountResponse.setAccountNumber("4444");
        createAccountRequest.setOwner("Kagan Esen");

        serviceResponse = new SuccessDataResult<>(createAccountResponse, "Account created successfully");

//        when(bankAccountService.createAccount(any(CreateAccountRequest.class))).thenReturn(serviceResponse);
    }

    @Test
    void createAccount() {

        when(bankAccountService.createAccount(createAccountRequest)).thenReturn((serviceResponse));

        DataResult<CreateAccountResponse> account = bankAccountController.createAccount(createAccountRequest);

        assertEquals(account, serviceResponse);


    }

    @Test
    void credit() {
    }

    @Test
    void debit() {
    }

    @Test
    void payment() {
    }

    @Test
    void getByAccountNumber() {
    }

    @Test
    void deleteAccount() {
    }
}
package com.eteration.simplebanking.services;

import com.eteration.simplebanking.common.util.result.DataResult;
import com.eteration.simplebanking.common.util.result.Result;
import com.eteration.simplebanking.model.dto.request.CreateAccountRequest;
import com.eteration.simplebanking.model.dto.request.CreateCreditRequest;
import com.eteration.simplebanking.model.dto.request.CreateDebitRequest;
import com.eteration.simplebanking.model.dto.request.CreatePaymentRequest;
import com.eteration.simplebanking.model.dto.response.*;


public interface BankAccountService {

    DataResult<CreateAccountResponse> createAccount(CreateAccountRequest createAccountRequest);

    DataResult<CreateCreditResponse> credit(CreateCreditRequest createCreditRequest);

    DataResult<CreateDebitResponse> debit (CreateDebitRequest createDebitRequest);

    DataResult<CreatePaymentResponse> payment (CreatePaymentRequest createPaymentRequest);

    DataResult<AccountDto> getByAccountNumber(String accountNumber);

    Result deleteAccount (String accountNumber);



}

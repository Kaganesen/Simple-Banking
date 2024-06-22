package com.eteration.simplebanking.services;

import com.eteration.simplebanking.common.util.result.DataResult;
import com.eteration.simplebanking.common.util.result.Result;
import com.eteration.simplebanking.model.dto.request.CreateAccountRequest;
import com.eteration.simplebanking.model.dto.request.CreditRequest;
import com.eteration.simplebanking.model.dto.request.DebitRequest;
import com.eteration.simplebanking.model.dto.request.PaymentRequest;
import com.eteration.simplebanking.model.dto.response.*;


public interface BankAccountService {

    DataResult<CreateAccountResponse> createAccount(CreateAccountRequest createAccountRequest);

    DataResult<CreditResponse> credit(CreditRequest creditRequest);

    DataResult<DebitResponse> debit (DebitRequest debitRequest);

    DataResult<PaymentResponse> payment (PaymentRequest paymentRequest);

    DataResult<AccountDto> getByAccountNumber(String accountNumber);

    Result deleteAccount (String accountNumber);



}

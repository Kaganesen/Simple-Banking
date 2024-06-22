package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.common.util.result.DataResult;
import com.eteration.simplebanking.common.util.result.Result;
import com.eteration.simplebanking.model.dto.request.CreateAccountRequest;
import com.eteration.simplebanking.model.dto.request.CreateCreditRequest;
import com.eteration.simplebanking.model.dto.request.CreateDebitRequest;
import com.eteration.simplebanking.model.dto.request.CreatePaymentRequest;
import com.eteration.simplebanking.model.dto.response.*;
import com.eteration.simplebanking.services.BankAccountService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;


    @PostMapping("/create")
    public DataResult<CreateAccountResponse> createAccount(@RequestBody @Valid CreateAccountRequest createAccountRequest) {
        return bankAccountService.createAccount(createAccountRequest);

    }

    @PostMapping("/credit")
    public DataResult<CreateCreditResponse> credit(@RequestBody @Valid CreateCreditRequest createCreditRequest) {
        return bankAccountService.credit(createCreditRequest);
    }


    @PostMapping("/debit")
    public DataResult<CreateDebitResponse> debit(@RequestBody @Valid CreateDebitRequest createDebitRequest) {
        return bankAccountService.debit(createDebitRequest);
    }

    @PostMapping("/payment")
    public DataResult<CreatePaymentResponse> payment(@RequestBody @Valid CreatePaymentRequest createPaymentRequest) {
        return bankAccountService.payment(createPaymentRequest);
    }

    @GetMapping("/account/{accountNumber}")
    public DataResult<AccountDto> getByAccountNumber(@PathVariable String accountNumber) {
        return bankAccountService.getByAccountNumber(accountNumber);
    }

    @DeleteMapping("/account/{accountNumber}")
    public Result deleteAccount (@PathVariable String accountNumber) {
        return bankAccountService.deleteAccount(accountNumber);
    }



}
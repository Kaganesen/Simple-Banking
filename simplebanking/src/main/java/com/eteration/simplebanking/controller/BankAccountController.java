package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.common.util.result.DataResult;
import com.eteration.simplebanking.common.util.result.Result;
import com.eteration.simplebanking.model.dto.request.CreateAccountRequest;
import com.eteration.simplebanking.model.dto.request.CreditRequest;
import com.eteration.simplebanking.model.dto.request.DebitRequest;
import com.eteration.simplebanking.model.dto.request.BillPaymentRequest;
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
    public DataResult<CreditResponse> credit(@RequestBody @Valid CreditRequest creditRequest) {
        return bankAccountService.credit(creditRequest);
    }


    @PostMapping("/debit")
    public DataResult<DebitResponse> debit(@RequestBody @Valid DebitRequest debitRequest) {
        return bankAccountService.debit(debitRequest);
    }

    @PostMapping("/bill-payment")
    public DataResult<PaymentResponse> billPayment(@RequestBody @Valid BillPaymentRequest billPaymentRequest) {
        return bankAccountService.billPayment(billPaymentRequest);
    }

    @GetMapping("/account/{accountNumber}")
    public DataResult<AccountDto> getByAccountNumber(@PathVariable String accountNumber) {
        return bankAccountService.getByAccountNumber(accountNumber);
    }

    @DeleteMapping("/delete/{accountNumber}")
    public Result deleteAccount(@PathVariable String accountNumber) {
        return bankAccountService.deleteAccount(accountNumber);
    }


}
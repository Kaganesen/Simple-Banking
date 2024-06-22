package com.eteration.simplebanking.services.impl;


import com.eteration.simplebanking.common.util.mapper.AccountMapper;
import com.eteration.simplebanking.common.util.result.DataResult;
import com.eteration.simplebanking.common.util.result.Result;
import com.eteration.simplebanking.common.util.result.SuccessDataResult;
import com.eteration.simplebanking.common.util.result.SuccessResult;
import com.eteration.simplebanking.exception.BankAccountNotFoundException;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.dto.request.CreateAccountRequest;
import com.eteration.simplebanking.model.dto.request.CreateCreditRequest;
import com.eteration.simplebanking.model.dto.request.CreateDebitRequest;
import com.eteration.simplebanking.model.dto.request.CreatePaymentRequest;
import com.eteration.simplebanking.model.dto.response.*;
import com.eteration.simplebanking.model.entity.BankAccount;
import com.eteration.simplebanking.model.entity.Transaction;
import com.eteration.simplebanking.repository.BankAccountRepository;
import com.eteration.simplebanking.services.BankAccountService;
import com.eteration.simplebanking.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    private final TransactionService transactionService;

    private final AccountMapper accountMapper;


    @Transactional
    public DataResult<CreateAccountResponse> createAccount(CreateAccountRequest createAccountRequest) {
        String accountNumber = generateUniqueAccountNumber();
        BankAccount account = accountMapper.createAccountRequestToBankAccount(createAccountRequest);
        account.setCreateDate(LocalDateTime.now());
        account.setAccountNumber(accountNumber);
        bankAccountRepository.save(account);

        CreateAccountResponse createAccountResponse = accountMapper.bankAccountToCreateAccountResponse(account);

        return new SuccessDataResult<>(createAccountResponse, "Account created successfully");
    }

    @Override
    @Transactional
    public DataResult<CreateCreditResponse> credit(CreateCreditRequest createCreditRequest) {
        BankAccount bankAccount = getBankAccount(createCreditRequest.getAccountNumber());
        bankAccount.setBalance(bankAccount.getBalance() + createCreditRequest.getAmount());
        bankAccountRepository.save(bankAccount);

        Transaction transaction = transactionService.deposit(bankAccount, createCreditRequest.getAmount());

        CreateCreditResponse createCreditResponse = accountMapper.createBankAccountToCreateCreditResponse(bankAccount);
        createCreditResponse.setApprovalCode(transaction.getApprovalCode());
        createCreditResponse.setTransactionDate(LocalDateTime.now());

        return new SuccessDataResult<>(createCreditResponse, "Credit created successfully");
    }


    @Override
    @Transactional
    public DataResult<CreateDebitResponse> debit(CreateDebitRequest createDebitRequest) {
        BankAccount bankAccount = getBankAccount(createDebitRequest.getAccountNumber());
        double requestAmount = createDebitRequest.getAmount();
        double currentBalance = bankAccount.getBalance();

        checkSufficientBalance(bankAccount, requestAmount);

        double updatedBalance = currentBalance - requestAmount;
        bankAccount.setBalance(updatedBalance);
        bankAccountRepository.save(bankAccount);

        Transaction transaction = transactionService.withdraw(bankAccount, createDebitRequest.getAmount());

        CreateDebitResponse createDebitResponse = accountMapper.createBankAccountToCreateDebitResponse(bankAccount);
        createDebitResponse.setApprovalCode(transaction.getApprovalCode());
        createDebitResponse.setTransactionDate(LocalDateTime.now());

        return new SuccessDataResult<>(createDebitResponse, "Debit created successfully");
    }

    @Override
    @Transactional
    public DataResult<CreatePaymentResponse> payment(CreatePaymentRequest createPaymentRequest) {
        BankAccount bankAccount = getBankAccount(createPaymentRequest.getAccountNumber());
        double requestAmount = createPaymentRequest.getAmount();
        double currentBalance = bankAccount.getBalance();

        checkSufficientBalance(bankAccount, requestAmount);

        double updatedBalance = currentBalance - requestAmount;

        bankAccount.setBalance(updatedBalance);
        bankAccountRepository.save(bankAccount);

        Transaction transaction = transactionService.payment(bankAccount, createPaymentRequest.getAmount());

        CreatePaymentResponse createPaymentResponse = accountMapper.createBankAccountToCreatePaymentResponse(bankAccount);
        createPaymentResponse.setApprovalCode(transaction.getApprovalCode());
        createPaymentResponse.setTransactionDate(LocalDateTime.now());

        return new SuccessDataResult<>(createPaymentResponse, "Payment created successfully");
    }

    @Override
    public DataResult<AccountDto> getByAccountNumber(String accountNumber) {
        BankAccount bankAccount = getBankAccount(accountNumber);

        AccountDto accountDto = accountMapper.accountDtoToBankAccount(bankAccount);

        return new SuccessDataResult<>(accountDto, "Account found");
    }

    @Override
    public Result deleteAccount(String accountNumber) {
        BankAccount bankAccount = getBankAccount(accountNumber);

        bankAccountRepository.delete(bankAccount);
        return new SuccessResult("Account deleted successfully: " + accountNumber);
    }

    public BankAccount getBankAccount(String accountNumber) {
        return bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank account not found: " + accountNumber));
    }

    private void checkSufficientBalance(BankAccount bankAccount, double requestAmount) {
        double currentBalance = bankAccount.getBalance();

        if (currentBalance < requestAmount) {
            throw new InsufficientBalanceException("Insufficient balance. Current balance: " + currentBalance);
        }
    }

    private String generateUniqueAccountNumber() {
        String accountNumber;
        do {
            accountNumber = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"))
                    + String.format("%04d", new Random().nextInt(10000));
        } while (accountNumberExists(accountNumber));
        return accountNumber;
    }

    private boolean accountNumberExists(String accountNumber) {
        Optional<BankAccount> existingAccount = bankAccountRepository.findByAccountNumber(accountNumber);
        return existingAccount.isPresent();
    }

}

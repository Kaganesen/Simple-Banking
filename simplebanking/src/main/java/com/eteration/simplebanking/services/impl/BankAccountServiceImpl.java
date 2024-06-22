package com.eteration.simplebanking.services.impl;


import com.eteration.simplebanking.common.util.mapper.AccountMapper;
import com.eteration.simplebanking.common.util.result.DataResult;
import com.eteration.simplebanking.common.util.result.Result;
import com.eteration.simplebanking.common.util.result.SuccessDataResult;
import com.eteration.simplebanking.common.util.result.SuccessResult;
import com.eteration.simplebanking.exception.BankAccountNotFoundException;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.dto.request.CreateAccountRequest;
import com.eteration.simplebanking.model.dto.request.CreditRequest;
import com.eteration.simplebanking.model.dto.request.DebitRequest;
import com.eteration.simplebanking.model.dto.request.PaymentRequest;
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
    public DataResult<CreditResponse> credit(CreditRequest creditRequest) {
        BankAccount bankAccount = getBankAccount(creditRequest.getAccountNumber());
        bankAccount.setBalance(bankAccount.getBalance() + creditRequest.getAmount());
        bankAccountRepository.save(bankAccount);

        Transaction transaction = transactionService.deposit(bankAccount, creditRequest.getAmount());

        CreditResponse creditResponse = accountMapper.createBankAccountToCreateCreditResponse(bankAccount);
        creditResponse.setApprovalCode(transaction.getApprovalCode());
        creditResponse.setTransactionDate(LocalDateTime.now());

        return new SuccessDataResult<>(creditResponse, "Credit created successfully");
    }


    @Override
    @Transactional
    public DataResult<DebitResponse> debit(DebitRequest debitRequest) {
        BankAccount bankAccount = getBankAccount(debitRequest.getAccountNumber());
        double requestAmount = debitRequest.getAmount();
        double currentBalance = bankAccount.getBalance();

        checkSufficientBalance(bankAccount, requestAmount);

        double updatedBalance = currentBalance - requestAmount;
        bankAccount.setBalance(updatedBalance);
        bankAccountRepository.save(bankAccount);

        Transaction transaction = transactionService.withdraw(bankAccount, debitRequest.getAmount());

        DebitResponse debitResponse = accountMapper.createBankAccountToCreateDebitResponse(bankAccount);
        debitResponse.setApprovalCode(transaction.getApprovalCode());
        debitResponse.setTransactionDate(LocalDateTime.now());

        return new SuccessDataResult<>(debitResponse, "Debit created successfully");
    }

    @Override
    @Transactional
    public DataResult<PaymentResponse> payment(PaymentRequest paymentRequest) {
        BankAccount bankAccount = getBankAccount(paymentRequest.getAccountNumber());
        double requestAmount = paymentRequest.getAmount();
        double currentBalance = bankAccount.getBalance();

        checkSufficientBalance(bankAccount, requestAmount);

        double updatedBalance = currentBalance - requestAmount;

        bankAccount.setBalance(updatedBalance);
        bankAccountRepository.save(bankAccount);

        Transaction transaction = transactionService.payment(bankAccount, paymentRequest.getAmount());

        PaymentResponse paymentResponse = accountMapper.createBankAccountToCreatePaymentResponse(bankAccount);
        paymentResponse.setApprovalCode(transaction.getApprovalCode());
        paymentResponse.setTransactionDate(LocalDateTime.now());

        return new SuccessDataResult<>(paymentResponse, "Payment created successfully");
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

package com.eteration.simplebanking.common.util.mapper;


import com.eteration.simplebanking.model.dto.request.CreateAccountRequest;
import com.eteration.simplebanking.model.dto.response.*;
import com.eteration.simplebanking.model.entity.BankAccount;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class AccountMapper {


    public BankAccount createAccountRequestToBankAccount(CreateAccountRequest createAccountRequest) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setOwner(createAccountRequest.getOwner());
        return bankAccount;
    }

    public abstract CreateAccountResponse bankAccountToCreateAccountResponse(BankAccount account);

    public abstract CreditResponse createBankAccountToCreateCreditResponse(BankAccount bankAccount);

    public abstract DebitResponse createBankAccountToCreateDebitResponse(BankAccount bankAccount);

    public abstract PaymentResponse createBankAccountToCreatePaymentResponse(BankAccount bankAccount);

    public abstract AccountDto accountDtoToBankAccount(BankAccount bankAccount);
}

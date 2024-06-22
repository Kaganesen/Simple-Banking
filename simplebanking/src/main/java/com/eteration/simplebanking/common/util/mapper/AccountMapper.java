package com.eteration.simplebanking.common.util.mapper;


import com.eteration.simplebanking.model.dto.request.*;
import com.eteration.simplebanking.model.dto.response.*;
import com.eteration.simplebanking.model.entity.BankAccount;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public abstract class AccountMapper {


    public BankAccount createAccountRequestToBankAccount(CreateAccountRequest createAccountRequest){
        BankAccount bankAccount = new BankAccount();
        bankAccount.setOwner(createAccountRequest.getOwner());
        return bankAccount;
    }

    public abstract CreateAccountResponse bankAccountToCreateAccountResponse(BankAccount account);

    @Mapping(target = "approvalCode", ignore = true)
    public abstract CreateCreditResponse createBankAccountToCreateCreditResponse(BankAccount bankAccount);

    @Mapping(target = "approvalCode", ignore = true)
    public abstract CreateDebitResponse createBankAccountToCreateDebitResponse(BankAccount bankAccount);

    @Mapping(target = "approvalCode", ignore = true)
    public abstract CreatePaymentResponse createBankAccountToCreatePaymentResponse(BankAccount bankAccount);

    public abstract AccountDto accountDtoToBankAccount(BankAccount bankAccount);
}

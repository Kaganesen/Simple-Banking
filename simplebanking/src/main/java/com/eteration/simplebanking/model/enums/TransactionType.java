package com.eteration.simplebanking.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {

    DEPOSIT("Deposit"),
    WITHDRAW("Withdraw"),
    BILL_PAYMENT("Bill Payment");


    private final String description;


}

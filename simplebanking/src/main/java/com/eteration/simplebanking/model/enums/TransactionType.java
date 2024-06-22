package com.eteration.simplebanking.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {

    DEPOSIT("Deposit Transaction"),
    WITHDRAW("Withdraw Transaction"),
    BILL_PAYMENT("Bill Payment Transaction");


    private final String description;


}

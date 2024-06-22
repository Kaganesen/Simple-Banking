package com.eteration.simplebanking.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateDepositResponse {

    private Long transactionId;

    private String accountNumber;

    private double amount;

    private double newBalance;

    private String message;
}

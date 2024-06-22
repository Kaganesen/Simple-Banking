package com.eteration.simplebanking.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionRequest {

    @NotBlank(message = "Transaction type must not be blank")
    private String transactionType;

    @Positive(message = "Amount must be positive")
    private double amount;

    @NotBlank(message = "Account number must not be blank")
    private String accountNumber;
}

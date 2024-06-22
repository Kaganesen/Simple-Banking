package com.eteration.simplebanking.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateWithdrawalRequest {

    @NotNull
    private String accountNumber;

    @NotNull
    private double amount;

}

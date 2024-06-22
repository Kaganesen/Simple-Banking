package com.eteration.simplebanking.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCreditRequest {

    @NotBlank
    private String accountNumber;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false , message = "Amount must be greater than 0")
    private Double amount;
}

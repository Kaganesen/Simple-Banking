package com.eteration.simplebanking.model.dto.request;

import com.eteration.simplebanking.common.util.messages.Messages;
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
public class DebitRequest {

    @NotBlank(message = Messages.ACCOUNT_NUMBER_NOT_BLANK)
    private String accountNumber;

    @Positive(message = Messages.AMOUNT_MUST_BE_POSITIVE)
    private double amount;
}

package com.eteration.simplebanking.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountResponse {

    private Long id;

    private String owner;

    private String accountNumber;

}

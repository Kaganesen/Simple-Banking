package com.eteration.simplebanking.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountResponse {

    private Long id;

    private String owner;

    private String accountNumber;

    private LocalDateTime createDate;
}

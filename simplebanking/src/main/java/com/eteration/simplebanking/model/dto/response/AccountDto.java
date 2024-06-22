package com.eteration.simplebanking.model.dto.response;

import com.eteration.simplebanking.model.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private String owner;

    private String accountNumber;

    private double balance;

    private LocalDateTime createDate;

    private List<Transaction> transactions;


}

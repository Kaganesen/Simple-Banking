package com.eteration.simplebanking.model.entity;


import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@DiscriminatorValue("WITHDRAW")
public class WithdrawalTransaction extends Transaction {

    public WithdrawalTransaction(double amount) {
        super(amount);
    }
}



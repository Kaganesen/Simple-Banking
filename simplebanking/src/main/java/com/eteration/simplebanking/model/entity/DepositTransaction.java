package com.eteration.simplebanking.model.entity;


import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@DiscriminatorValue("DEPOSIT")
public class DepositTransaction extends Transaction {

    public DepositTransaction(double amount) {
        super(amount);
    }

}

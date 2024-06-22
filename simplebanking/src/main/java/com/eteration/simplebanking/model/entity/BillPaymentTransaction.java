package com.eteration.simplebanking.model.entity;

import lombok.NoArgsConstructor;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@DiscriminatorValue("BILL_PAYMENT")
public class BillPaymentTransaction extends Transaction {

    private String payee;

    public BillPaymentTransaction(double amount) {
        super(amount);
    }

}

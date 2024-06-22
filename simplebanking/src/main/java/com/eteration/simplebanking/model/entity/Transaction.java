package com.eteration.simplebanking.model.entity;


import com.eteration.simplebanking.model.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "transaction_type", discriminatorType = DiscriminatorType.STRING)
@Entity
public abstract class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;

    private double amount;

    private String approvalCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", insertable = false, updatable = false)
    private TransactionType transactionType;

    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    @JsonBackReference
    private BankAccount bankAccount;

    public Transaction(double amount) {
        this.date = LocalDateTime.now();
        this.amount = amount;
    }
}

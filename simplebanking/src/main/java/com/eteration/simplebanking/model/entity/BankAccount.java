package com.eteration.simplebanking.model.entity;

import com.eteration.simplebanking.common.util.messages.Messages;
import com.eteration.simplebanking.exception.InvalidTransactionException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String owner;

    @Column(unique = true)
    private String accountNumber;

    private LocalDateTime createDate;

    private double balance;

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Transaction> transactions;


    public BankAccount(String accountNumber, String owner) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    public void post(Transaction transaction) {
        if (transaction == null) {
            throw new InvalidTransactionException(Messages.TRANSACTION_CANNOT_BE_NULL);
        }
        transactions.add(transaction);
        transaction.setBankAccount(this);
    }

}

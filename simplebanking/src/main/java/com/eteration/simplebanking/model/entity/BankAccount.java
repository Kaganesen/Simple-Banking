package com.eteration.simplebanking.model.entity;

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

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        balance += amount;
        Transaction transaction = new DepositTransaction(amount);
        this.transactions.add(transaction);
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Amount must be less than balance");
        }
        balance -= amount;
        this.transactions.add(new WithdrawalTransaction(amount));
    }

    public void post(Transaction transaction) {
        transactions.add(transaction);
        transaction.setBankAccount(this);
    }

}

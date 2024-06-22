package com.eteration.simplebanking.common.util.factory;

import com.eteration.simplebanking.model.entity.BillPaymentTransaction;
import com.eteration.simplebanking.model.entity.DepositTransaction;
import com.eteration.simplebanking.model.entity.Transaction;
import com.eteration.simplebanking.model.entity.WithdrawalTransaction;
import com.eteration.simplebanking.model.enums.TransactionType;
import org.springframework.stereotype.Component;

@Component
public class TransactionFactory {

    public Transaction createTransaction(TransactionType type, double amount) {
        switch (type) {
            case DEPOSIT:
                return new DepositTransaction(amount);

            case WITHDRAW:
                return new WithdrawalTransaction(amount);

            case BILL_PAYMENT:
                return new BillPaymentTransaction(amount);

            default:
                throw new IllegalArgumentException("Unsupported transaction type: " + type);
        }

    }
}

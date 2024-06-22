package com.eteration.simplebanking;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.eteration.simplebanking.model.entity.BankAccount;
import com.eteration.simplebanking.model.entity.DepositTransaction;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.entity.WithdrawalTransaction;

import com.eteration.simplebanking.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;


public class ModelTest {

	@Mock
	private TransactionRepository transactionRepository;

	private BankAccount bankAccount;

	@BeforeEach
	public void setUp() {
		transactionRepository = mock(TransactionRepository.class);
		bankAccount = new BankAccount("17892", "Kerem Karaca");
		bankAccount.setBalance(1000);
	}
	
	@Test
	public void testCreateAccountAndSetBalance0() {
		BankAccount account = new BankAccount("17892", "Kerem Karaca");
		assertTrue(account.getOwner().equals("Kerem Karaca"));
		assertTrue(account.getAccountNumber().equals("17892"));
		assertTrue(account.getBalance() == 0);
	}

	@Test
	public void deposit_ValidAmount_ShouldIncreaseBalanceAndTransaction() {
		double amount = 500.0;

		bankAccount.deposit(amount);
		assertEquals(1500.0, bankAccount.getBalance());
		assertEquals(1, bankAccount.getTransactions().size());
		assertTrue(bankAccount.getTransactions().get(0) instanceof DepositTransaction);

	}

	@Test
	public void testDepositIntoBankAccount() {
		BankAccount account = new BankAccount("9834", "Demet Demircan");
		account.deposit(100);
		assertTrue(account.getBalance() == 100);
	}

	@Test
	public void testWithdrawFromBankAccount() throws InsufficientBalanceException {
		BankAccount account = new BankAccount("9834", "Demet Demircan");
		account.deposit(100);
		assertTrue(account.getBalance() == 100);
		account.withdraw(50);
		assertTrue(account.getBalance() == 50);
	}

	@Test
	public void testWithdrawException() {
		Assertions.assertThrows( InsufficientBalanceException.class, () -> {
			BankAccount account = new BankAccount("9834", "Demet Demircan");
			account.deposit(100);
			account.withdraw(500);
		  });

	}
	
	@Test
	public void testTransactions() throws InsufficientBalanceException {
		// Create account
		BankAccount account = new BankAccount("1234", "Canan Kaya");
		assertTrue(account.getTransactions().size() == 0);

		// Deposit Transaction
		DepositTransaction depositTrx = new DepositTransaction(100);
		assertTrue(depositTrx.getDate() != null);
		account.post(depositTrx);
		assertTrue(account.getBalance() == 100);
		assertTrue(account.getTransactions().size() == 1);

		// Withdrawal Transaction
		WithdrawalTransaction withdrawalTrx = new WithdrawalTransaction(60);
		assertTrue(withdrawalTrx.getDate() != null);
		account.post(withdrawalTrx);
		assertTrue(account.getBalance() == 40);
		assertTrue(account.getTransactions().size() == 2);
	}
}

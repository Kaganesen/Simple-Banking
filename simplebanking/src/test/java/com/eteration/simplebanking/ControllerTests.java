//package com.eteration.simplebanking;
//
//import com.eteration.simplebanking.controller.BankAccountController;
//import com.eteration.simplebanking.model.entity.BankAccount;
//import com.eteration.simplebanking.model.entity.DepositTransaction;
//import com.eteration.simplebanking.model.entity.WithdrawalTransaction;
//import com.eteration.simplebanking.services.BankAccountService;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.Spy;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ContextConfiguration;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//@ContextConfiguration
//@AutoConfigureMockMvc
//class ControllerTests {
//
//    @Spy
//    @InjectMocks
//    private BankAccountController controller;
//
//    @Mock
//    private BankAccountService service;
//
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        controller = new BankAccountController(service);
//    }
//
//    @Test
//    public void givenId_Credit_thenReturnJson() throws Exception {
//
//        BankAccount account = new BankAccount("Kerem Karaca", "17892");
//
//        doReturn(account).when(service).getByAccountNumber("17892");
//        ResponseEntity<TransactionStatus> result = controller.credit("17892", new DepositTransaction(1000.0));
//        verify(service, times(1)).getByAccountNumber("17892");
//        assertEquals("OK", result.getBody().getStatus());
//    }
//
//    @Test
//    public void givenId_CreditAndThenDebit_thenReturnJson() throws Exception {
//
//        BankAccount account = new BankAccount("Kerem Karaca", "17892");
//
//        doReturn(account).when(service).getByAccountNumber("17892");
//        ResponseEntity<TransactionStatus> result = controller.credit("17892", new DepositTransaction(1000.0));
//        ResponseEntity<TransactionStatus> result2 = controller.debit("17892", new WithdrawalTransaction(50.0));
//        verify(service, times(2)).getByAccountNumber("17892");
//        assertEquals("OK", result.getBody().getStatus());
//        assertEquals("OK", result2.getBody().getStatus());
//        assertEquals(950.0, account.getBalance(), 0.001);
//    }
//
//    @Test
//    public void givenId_CreditAndThenDebitMoreGetException_thenReturnJson() throws Exception {
//        Assertions.assertThrows(InsufficientBalanceException.class, () -> {
//            BankAccount account = new BankAccount("Kerem Karaca", "17892");
//
//            doReturn(account).when(service).getByAccountNumber("17892");
//            ResponseEntity<TransactionStatus> result = controller.credit("17892", new DepositTransaction(1000.0));
//            assertEquals("OK", result.getBody().getStatus());
//            assertEquals(1000.0, account.getBalance(), 0.001);
//            verify(service, times(1)).getByAccountNumber("17892");
//
//            ResponseEntity<TransactionStatus> result2 = controller.debit("17892", new WithdrawalTransaction(5000.0));
//        });
//    }
//
//    @Test
//    public void givenId_GetAccount_thenReturnJson() throws Exception {
//
//        BankAccount account = new BankAccount("Kerem Karaca", "17892");
//
//        doReturn(account).when(service).getByAccountNumber("17892");
//        ResponseEntity<AccountDto> result = controller.getAccountByAccountNumber("17892");
//        verify(service, times(1)).getByAccountNumber("17892");
//        assertEquals(account, result.getBody());
//    }
//
//}

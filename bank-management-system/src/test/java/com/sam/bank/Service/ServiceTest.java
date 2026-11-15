
package com.sam.bank.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sam.bank.dao.AccountDAO;
import com.sam.bank.dao.TransactionDAO;
import com.sam.bank.enums.AccountStatus;
import com.sam.bank.enums.TransactionType;
import com.sam.bank.interfaces.SqlOperation;
import com.sam.bank.model.Account;
import com.sam.bank.model.Transaction;
import com.sam.bank.service.TransactionService;
import com.sam.bank.util.JdbcTransaction;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionDAO transactionDAO;

    @Mock
    private AccountDAO accountDAO;

    @Mock
    private JdbcTransaction jdbcTransaction;

    @Mock
    private Connection connection;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
    }

    private void mockTransactionExecution() throws SQLException {
        doAnswer(invocation -> {
            SqlOperation operation = invocation.getArgument(0);
            operation.execute(connection);
            return null;
        }).when(jdbcTransaction).execute(any(SqlOperation.class));
    }

    private Account mockAccount(long id, String balance) {
        Account account = mock(Account.class);

        when(account.getAccountId()).thenReturn(id);
        when(account.getBalance()).thenReturn(new BigDecimal(balance));

        return account;
    }


    @Test
    void depositIncreasesAccountBalance() throws SQLException {
        mockTransactionExecution();

        Account account = mockAccount(1L, "100.00");

        transactionService.deposit(account, new BigDecimal("50.00"));

        verify(accountDAO).updateBalance(
            connection, 1L, new BigDecimal("150.00")
        );
    }

    @Test
    void depositCreatesTransactionRecord() throws SQLException {
        mockTransactionExecution();

        Account account = mockAccount(1L, "100.00");

        transactionService.deposit(account, new BigDecimal("50.00"));

        ArgumentCaptor<Transaction> captor =
            ArgumentCaptor.forClass(Transaction.class);

        verify(transactionDAO).createTransaction(
            eq(connection), captor.capture()
        );

        Transaction transaction = captor.getValue();

        assertEquals(TransactionType.DEPOSIT,
            transaction.getTransactionType());

        assertEquals(new BigDecimal("50.00"), transaction.getAmount());
        assertEquals(new BigDecimal("150.00"),
            transaction.getDestBalanceAfter());

        assertNull(transaction.getSourceAccountId());
        assertEquals(1L, transaction.getDestinationAccountId());
    }

    @Test
    void depositRejectsZeroAmount() {
        Account account = mock(Account.class);

        assertThrows(IllegalArgumentException.class,
            () -> transactionService.deposit(account, BigDecimal.ZERO));

        verifyNoInteractions(jdbcTransaction);
    }

    @Test
    void depositRejectsNegativeAmount() {
        Account account = mock(Account.class);

        assertThrows(IllegalArgumentException.class,
            () -> transactionService.deposit(
                account, new BigDecimal("-10.00")
            ));

        verifyNoInteractions(jdbcTransaction);
    }


    @Test
    void withdrawalDecreasesAccountBalance() throws SQLException {
        mockTransactionExecution();

        Account account = mockAccount(1L, "100.00");

        transactionService.withdrawal(account, new BigDecimal("30.00"));

        verify(accountDAO).updateBalance(
            connection, 1L, new BigDecimal("70.00")
        );
    }

    @Test
    void withdrawalCreatesTransactionRecord() throws SQLException {
        mockTransactionExecution();

        Account account = mockAccount(1L, "100.00");

        transactionService.withdrawal(account, new BigDecimal("30.00"));

        ArgumentCaptor<Transaction> captor =
            ArgumentCaptor.forClass(Transaction.class);

        verify(transactionDAO).createTransaction(
            eq(connection), captor.capture()
        );

        Transaction transaction = captor.getValue();

        assertEquals(TransactionType.WITHDRAWAL,
            transaction.getTransactionType());

        assertEquals(new BigDecimal("30.00"), transaction.getAmount());
        assertEquals(new BigDecimal("70.00"),
            transaction.getSourceBalanceAfter());

        assertNull(transaction.getDestinationAccountId());
    }

    @Test
    void withdrawalRejectsInsufficientFunds() {
        Account account = mockAccount(1L, "50.00");

        assertThrows(IllegalArgumentException.class,
            () -> transactionService.withdrawal(
                account, new BigDecimal("100.00")
            ));

        verifyNoInteractions(jdbcTransaction);
    }

    @Test
    void withdrawalRejectsZeroAmount() {
        Account account = mock(Account.class);
        when(account.getAccountId()).thenReturn(1L);

        assertThrows(IllegalArgumentException.class,
            () -> transactionService.withdrawal(
                account, BigDecimal.ZERO
            ));

        verifyNoInteractions(jdbcTransaction);
    }


    @Test
    void transferUpdatesBothAccountBalances() throws SQLException {
        mockTransactionExecution();

        Account from = mockAccount(1L, "200.00");
        Account to = mockAccount(2L, "100.00");

        transactionService.transfer(
            from, to, new BigDecimal("50.00")
        );

        verify(accountDAO).updateBalance(
            connection, 1L, new BigDecimal("150.00")
        );

        verify(accountDAO).updateBalance(
            connection, 2L, new BigDecimal("150.00")
        );
    }

    @Test
    void transferCreatesTransactionRecord() throws SQLException {
        mockTransactionExecution();

        Account from = mockAccount(1L, "200.00");
        Account to = mockAccount(2L, "100.00");

        transactionService.transfer(
            from, to, new BigDecimal("50.00")
        );

        ArgumentCaptor<Transaction> captor =
            ArgumentCaptor.forClass(Transaction.class);

        verify(transactionDAO).createTransaction(
            eq(connection), captor.capture()
        );

        Transaction transaction = captor.getValue();

        assertEquals(TransactionType.TRANSFER,
            transaction.getTransactionType());

        assertEquals(1L, transaction.getSourceAccountId());
        assertEquals(2L, transaction.getDestinationAccountId());

        assertEquals(new BigDecimal("150.00"),
            transaction.getSourceBalanceAfter());

        assertEquals(new BigDecimal("150.00"),
            transaction.getDestBalanceAfter());
    }

    @Test
    void transferRejectsInsufficientFunds() {
        Account from = mockAccount(1L, "50.00");
        Account to = mockAccount(2L, "100.00");

        assertThrows(IllegalArgumentException.class,
            () -> transactionService.transfer(
                from, to, new BigDecimal("100.00")
            ));

        verifyNoInteractions(jdbcTransaction);
    }

    @Test
    void transferRejectsSameAccount() {
        Account account = mockAccount(1L, "100.00");

        assertThrows(IllegalArgumentException.class,
            () -> transactionService.transfer(
                account, account, new BigDecimal("10.00")
            ));

        verifyNoInteractions(jdbcTransaction);
    }


    @Test
    void loanRepaymentUpdatesBothBalances() throws SQLException {
        mockTransactionExecution();

        Account funding = mockAccount(1L, "200.00");
        Account loan = mockAccount(2L, "500.00");

        transactionService.repayLoan(
            funding, loan, new BigDecimal("100.00")
        );

        verify(accountDAO).updateBalance(
            connection, 1L, new BigDecimal("100.00")
        );

        verify(accountDAO).updateBalance(
            connection, 2L, new BigDecimal("400.00")
        );
    }

    @Test
    void loanRepaymentCreatesTransactionRecord() throws SQLException {
        mockTransactionExecution();

        Account funding = mockAccount(1L, "200.00");
        Account loan = mockAccount(2L, "500.00");

        transactionService.repayLoan(
            funding, loan, new BigDecimal("100.00")
        );

        ArgumentCaptor<Transaction> captor =
            ArgumentCaptor.forClass(Transaction.class);

        verify(transactionDAO).createTransaction(
            eq(connection), captor.capture()
        );

        Transaction transaction = captor.getValue();

        assertEquals(TransactionType.LOAN_REPAYMENT,
            transaction.getTransactionType());

        assertEquals(1L, transaction.getSourceAccountId());
        assertEquals(2L, transaction.getDestinationAccountId());

        assertEquals(new BigDecimal("100.00"), transaction.getAmount());

        assertEquals(new BigDecimal("100.00"),
            transaction.getSourceBalanceAfter());

        assertEquals(new BigDecimal("400.00"),
            transaction.getDestBalanceAfter());
    }

    @Test
    void loanRepaymentRejectsInsufficientFunds() {
        Account funding = mockAccount(1L, "50.00");
        Account loan = mockAccount(2L, "500.00");

        assertThrows(IllegalArgumentException.class,
            () -> transactionService.repayLoan(
                funding, loan, new BigDecimal("100.00")
            ));

        verifyNoInteractions(jdbcTransaction);
    }

    @Test
    void loanRepaymentRejectsOverpayment() {
        Account funding = mockAccount(1L, "500.00");
        Account loan = mockAccount(2L, "100.00");

        assertThrows(IllegalArgumentException.class,
            () -> transactionService.repayLoan(
                funding, loan, new BigDecimal("200.00")
            ));

        verifyNoInteractions(jdbcTransaction);
    }

    @Test
    void fullyRepaidLoanIsClosed() throws SQLException {
        mockTransactionExecution();

        Account funding = mockAccount(1L, "500.00");
        Account loan = mockAccount(2L, "100.00");

        transactionService.repayLoan(
            funding, loan, new BigDecimal("100.00")
        );

        verify(accountDAO).updateStatus(
            connection, 2L, AccountStatus.CLOSED
        );
    }


    @Test
    void getHistoryReturnsCustomerTransactions() throws SQLException {
        ArrayList<Transaction> expected = new ArrayList<>();

        when(transactionDAO.getHistory(1L)).thenReturn(expected);

        ArrayList<Transaction> actual =
            transactionService.getHistory(1L);

        assertSame(expected, actual);
        verify(transactionDAO).getHistory(1L);
    }
}

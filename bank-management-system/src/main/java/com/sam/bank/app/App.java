package com.sam.bank.app;

import com.sam.bank.dao.AccountDAO;
import com.sam.bank.dao.CustomerDAO;
import com.sam.bank.dao.DepositDAO;
import com.sam.bank.dao.LoanDAO;
import com.sam.bank.security.BCryptPasswordHasher;
import com.sam.bank.security.PasswordHasher;
import com.sam.bank.security.SessionContext;
import com.sam.bank.security.SessionManager;
import com.sam.bank.service.AccountService;
import com.sam.bank.service.AuthService;
import com.sam.bank.util.AppConfig;
import com.sam.bank.util.ConnectionFactory;
import com.sam.bank.util.JdbcTransaction;

public class App 
{
    public static void main( String[] args )
    {
        AppConfig app = new AppConfig();
        PasswordHasher passwordHasher = new BCryptPasswordHasher();
        ConnectionFactory connection = new ConnectionFactory(app);
        JdbcTransaction transaction = new JdbcTransaction(connection);

        ConsoleInput input = new ConsoleInput();

        SessionContext currentSession = new SessionContext();
        SessionManager session = new SessionManager(currentSession);

        CustomerDAO customerDAO = new CustomerDAO(connection);
        AccountDAO accountDAO = new AccountDAO(connection);
        LoanDAO loanDAO = new LoanDAO();
        DepositDAO depositDAO = new DepositDAO();

        AuthService authService = new AuthService(customerDAO, passwordHasher, session);
        AccountService accountService = new AccountService(accountDAO, loanDAO, depositDAO, session, transaction);

        LoginMenu loginMenu = new LoginMenu(input, authService);
        CustomerMenu customerMenu = new CustomerMenu(input, authService, accountService);

        MainMenu menu = new MainMenu(session, customerMenu, loginMenu);

        menu.loadMenu();

    }
}

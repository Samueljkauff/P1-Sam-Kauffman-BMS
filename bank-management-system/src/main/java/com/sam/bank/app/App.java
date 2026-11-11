package com.sam.bank.app;

import com.sam.bank.dao.AccountDAO;
import com.sam.bank.dao.CustomerDAO;
import com.sam.bank.security.BCryptPasswordHasher;
import com.sam.bank.security.PasswordHasher;
import com.sam.bank.security.SessionContext;
import com.sam.bank.security.SessionManager;
import com.sam.bank.service.AccountService;
import com.sam.bank.service.AuthService;
import com.sam.bank.util.AppConfig;
import com.sam.bank.util.ConnectionFactory;

public class App 
{
    public static void main( String[] args )
    {
        AppConfig app = new AppConfig();
        PasswordHasher passwordHasher = new BCryptPasswordHasher();
        ConnectionFactory connection = new ConnectionFactory(app);

        ConsoleInput input = new ConsoleInput();

        SessionContext currentSession = new SessionContext();
        SessionManager session = new SessionManager(currentSession);

        CustomerDAO customerDAO = new CustomerDAO(connection);
        AccountDAO accountDOA = new AccountDAO(connection);

        AuthService authService = new AuthService(customerDAO, passwordHasher, session);
        AccountService accountService = new AccountService(session);

        LoginMenu loginMenu = new LoginMenu(input, authService);
        CustomerMenu customerMenu = new CustomerMenu(input, authService, accountService);

        MainMenu menu = new MainMenu(session, customerMenu, loginMenu);

        menu.loadMenu();

    }
}

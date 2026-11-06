package com.sam.bank.app;

import com.sam.bank.dao.CustomerDAO;
import com.sam.bank.security.BCryptPasswordHasher;
import com.sam.bank.security.PasswordHasher;
import com.sam.bank.security.SessionManager;
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

        // EVENTUALLY MAKING DAOS WITH CONNECTION

        SessionManager session = new SessionManager();
        CustomerDAO customerDAO = new CustomerDAO(connection);
        AuthService authService = new AuthService(customerDAO, passwordHasher);

        ConsoleInput input = new ConsoleInput();
        LoginMenu loginMenu = new LoginMenu(input, authService);
        CustomerMenu customerMenu = new CustomerMenu(input);

        MainMenu menu = new MainMenu(session, customerMenu, loginMenu);

        menu.loadMenu();

    }
}

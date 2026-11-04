package com.sam.bank.app;

import com.sam.bank.security.SessionManager;
import com.sam.bank.service.AuthService;

public class App 
{
    public static void main( String[] args )
    {
        ConsoleInput input = new ConsoleInput();
        SessionManager session = new SessionManager();
        AuthService authService = new AuthService();

        LoginMenu loginMenu = new LoginMenu(input, authService);
        CustomerMenu customerMenu = new CustomerMenu(input);

        MainMenu menu = new MainMenu(session, customerMenu, loginMenu);

        menu.loadMenu();

    }
}

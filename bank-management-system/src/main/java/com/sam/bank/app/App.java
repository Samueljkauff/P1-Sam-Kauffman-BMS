package com.sam.bank.app;

import com.sam.bank.security.SessionManager;

public class App 
{
    public static void main( String[] args )
    {
        ConsoleInput input = new ConsoleInput();
        SessionManager session = new SessionManager();

        LoginMenu loginMenu = new LoginMenu(input);
        CustomerMenu customerMenu = new CustomerMenu(input);

        MainMenu menu = new MainMenu(session, customerMenu, loginMenu);

        menu.loadMenu();

    }
}

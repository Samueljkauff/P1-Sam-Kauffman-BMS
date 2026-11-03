package com.sam.bank.app;

import com.sam.bank.security.SessionManager;

public class MainMenu {
    private SessionManager session;
    private CustomerMenu customerMenu;
    private LoginMenu loginMenu;

    public MainMenu(SessionManager session, CustomerMenu customerMenu, LoginMenu loginMenu) {
        this.session = session;
        this.customerMenu = customerMenu;
        this.loginMenu = loginMenu;
    }   

    public void loadMenu() {
        System.out.println("-------- BMS --------");
        if(session.isLoggedIn()) {
            customerMenu.loadMenu();
        } else {
            loginMenu.loadMenu();
        }
    }
}

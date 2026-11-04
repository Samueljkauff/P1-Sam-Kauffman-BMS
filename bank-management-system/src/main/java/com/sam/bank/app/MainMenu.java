package com.sam.bank.app;

import com.sam.bank.enums.Navigation;
import com.sam.bank.security.SessionManager;

public class MainMenu {
    private SessionManager session;
    private CustomerMenu customerMenu;
    private LoginMenu loginMenu;
    public Navigation navType;

    public MainMenu(SessionManager session, CustomerMenu customerMenu, LoginMenu loginMenu) {
        this.session = session;
        this.customerMenu = customerMenu;
        this.loginMenu = loginMenu;
        this.navType = Navigation.CONTINUE;
    }   

    public void loadMenu() {
        System.out.println("-------- BMS --------");
        while(navType != Navigation.QUIT) {
            if(session.isLoggedIn()) {
                navType = customerMenu.loadMenu();
            } else {
                navType = loginMenu.loadMenu();
            }
        }
    }
}

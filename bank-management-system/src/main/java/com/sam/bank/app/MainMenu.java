package com.sam.bank.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sam.bank.enums.Navigation;
import com.sam.bank.model.Account;
import com.sam.bank.security.SessionManager;
import com.sam.bank.service.AccountService;

public class MainMenu {
    private SessionManager session;
    private CustomerMenu customerMenu;
    private LoginMenu loginMenu;
    private AccountService accountService;
    public Navigation navType;

    public MainMenu(SessionManager session, CustomerMenu customerMenu, LoginMenu loginMenu, AccountService accountService) {
        this.session = session;
        this.customerMenu = customerMenu;
        this.loginMenu = loginMenu;
        this.accountService = accountService;
        this.navType = Navigation.CONTINUE;
    }   

    public void loadMenu() {
        System.out.println("-------- BMS --------");
        List<Account> accounts = new ArrayList<>();
        while(navType != Navigation.QUIT) {
            if(session.isLoggedIn()) {
                try {
                    accounts = accountService.getAllAccounts(true, false);
                } catch(SQLException e) {
                    System.out.println(e);
                }
                if(accounts.isEmpty()) {
                    navType = customerMenu.loadInitMenu();
                } else {
                    navType = customerMenu.loadMenu();
                }
            } else {
                navType = loginMenu.loadMenu();
            }
        }
    }
}

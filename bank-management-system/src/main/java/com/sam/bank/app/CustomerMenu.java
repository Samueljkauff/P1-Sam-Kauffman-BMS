package com.sam.bank.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

import com.sam.bank.enums.AccountType;
import com.sam.bank.enums.Navigation;
import com.sam.bank.service.AccountService;
import com.sam.bank.service.AuthService;

public class CustomerMenu {
    private ConsoleInput input;
    private AuthService authService;
    private AccountService accountService;

    public CustomerMenu(ConsoleInput input, AuthService authService, AccountService accountService) {
        this.input = input;
        this.authService = authService;
        this.accountService = accountService;
    }

    public Navigation loadMenu() {
        System.out.println("Enter the following selections to continue.");
        System.out.println(" 1. Deposit\n 2. Withdrawl \n 3. Transfer \n 4. Balance \n 5. Repay Loan \n 6. Create Account \n 7. History \n 8. Profile \n 9. Logout \n 10. Quit");

        int choice = input.readInt();
        return handleInput(choice);
    }

    private Navigation handleInput(int choice) {
        switch (choice) {
            case 1:

                return Navigation.CONTINUE;
            case 2:

                return Navigation.CONTINUE;
            case 3:
             
                return Navigation.CONTINUE;
            case 4:

                return Navigation.CONTINUE;
            case 5:

                return Navigation.CONTINUE;
            case 6:
                AccountType type = accountChoice();
                if (type == null) {
                    return Navigation.CONTINUE;
                }
                accountCreationFlow(type);
                return Navigation.CONTINUE;
            case 7:

                return Navigation.CONTINUE;
            case 8:

                return Navigation.CONTINUE;
            case 9:
                logout();
                return Navigation.LOGOUT;
            case 10:
                System.out.println("Goodbye Now");
                return Navigation.QUIT;
            default:
                System.out.println("Invalid Choice!!! Input must be a number 1-10.\n");
                loadMenu();
                return Navigation.CONTINUE;
        }
    }

    private void logout() {
        authService.logout();
    }

    private AccountType accountChoice() {
        while (true) {
            System.out.println("\nWhat kind of account would you like to create?");
            System.out.println("1. Savings");
            System.out.println("2. Current");
            System.out.println("3. Deposit");
            System.out.println("4. Loan");
            System.out.println("5. Cancel");

            int choice = input.readInt();

            switch (choice) {
                case 1:
                    return AccountType.SAVINGS;
                case 2:
                    return AccountType.CURRENT;
                case 3:
                    return AccountType.DEPOSIT;
                case 4:
                    return AccountType.LOAN;
                case 5:
                    return null;
                default:
                    System.out.println("Invalid choice! Enter a number between 1 and 5.");
            }
        }
    }
    public boolean accountCreationFlow(AccountType type) {


        switch(type) {
            case SAVINGS:
                System.out.println("Enter a minimum balance for you savings (round to the nearest dollar):");
                BigDecimal minBal = input.readDecimal();
                try {
                    accountService.savingCreation(type, minBal);
                } catch(SQLException e) {
                    System.out.println("Something Went wrong. Please Try again.");
                }
                return true;
            case CURRENT:
                System.out.println("Enter an overdraft limit (round to the nearest dollar):");
                BigDecimal overdraftLimit = input.readDecimal();
                try {
                    accountService.currentCreation(type, overdraftLimit);
                } catch(SQLException e) {
                    System.out.println("Something Went wrong. Please Try again.");
                }
                return true;
            case DEPOSIT:
                System.out.println("Deposit an initial amount:");
                BigDecimal initAmount = input.readDecimal();
                LocalDate maturityDate = maturityDates();
                // THIS WILL BE A LONG LATER
                Long fundingAccount = chooseFundingAccount();
                try {
                    accountService.depositCreation(type, initAmount, maturityDate, fundingAccount);
                } catch(SQLException e) {
                    System.out.println("Something Went wrong. Please Try again.");
                }
                return true;
            case LOAN:
                System.out.println("How much money would you like to borrow: \n");
                BigDecimal principal = input.readDecimal();
                try {
                    accountService.loanCreation(type, principal);
                } catch(SQLException e) {
                    System.out.println("Something Went wrong. Please Try again.");
                }
                return true;
            default:
                System.out.println("ERROR: something went wrong");
                return false;
        }
    }

    public LocalDate maturityDates() {
        System.out.println("Select a maturity date: \n 1. 3 month \n2. 6 months \n3. 12 months");
        int choice = input.readInt();

        LocalDate maturityDate;
        while(true) {
            switch (choice) {
                case 1:
                    maturityDate = LocalDate.now().plusMonths(3);
                    return maturityDate;
                case 2:
                    maturityDate = LocalDate.now().plusMonths(6);
                    return maturityDate;
                case 3:
                    maturityDate = LocalDate.now().plusMonths(12);
                    return maturityDate;
                default:
                    System.out.println("\nEnter a valid selection");
            }
        }
    }

    public Long chooseFundingAccount() {
        // Implement this later (Should list all accounts)
        return null;
    }
}

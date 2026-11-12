package com.sam.bank.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.sam.bank.enums.AccountType;
import com.sam.bank.enums.Navigation;
import com.sam.bank.service.AccountService;
import com.sam.bank.service.AuthService;
import com.sam.bank.service.TransactionService;
import com.sam.bank.model.Account;

public class CustomerMenu {
    private ConsoleInput input;
    private AuthService authService;
    private AccountService accountService;
    private TransactionService transactionService;

    public CustomerMenu(ConsoleInput input, AuthService authService, AccountService accountService, TransactionService transactionService) {
        this.input = input;
        this.authService = authService;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    public Navigation loadMenu() {
        System.out.println("Enter the following selections to continue.");
        System.out.println(" 1. Deposit\n 2. Withdrawal \n 3. Transfer \n 4. View Accounts \n 5. Repay Loan \n 6. Create Account \n 7. History \n 8. Profile \n 9. Logout \n 10. Quit");

        int choice = input.readInt();
        return handleInput(choice);
    }

    private Navigation handleInput(int choice) {
        switch (choice) {
            case 1:
                depositFlow();
                return Navigation.CONTINUE;
            case 2:
                withdrawalFlow();
                return Navigation.CONTINUE;
            case 3:
                transferFlow();
                return Navigation.CONTINUE;
            case 4:
                String isDone;
                do {
                    listAccounts(true, false);
                    System.out.println("press q to go back");
                    isDone = input.readString();
                } while(!isDone.equals("q"));

                loadMenu();
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

    public void depositFlow() {
        String prompt = "Which account would you like to deposit to?";
        Account account = chooseAccount(prompt);

        System.out.println("How much would you like to deposit?");
        BigDecimal depositAmount = input.readDecimal();
        try {
            transactionService.deposit(account, depositAmount);
        } catch(SQLException e) {
            System.out.println("Soemthing went wrong: " + e);
            loadMenu();
        }
    }

    public void withdrawalFlow() {
        String prompt = "Which account would you like to withdrawal from?";
        Account account = chooseAccount(prompt);

        System.out.println("How much would you like to withdrawal?");
        BigDecimal withdrawalAmount = input.readDecimal();
        try {
            transactionService.withdrawal(account, withdrawalAmount);
        } catch(SQLException e) {
            System.out.println("Something went wrong: " + e);
        }

    }

    public void transferFlow() {

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


        BigDecimal initAmount = BigDecimal.ZERO;
        switch(type) {
            case SAVINGS:
                System.out.println("Enter a minimum balance for you savings:");
                BigDecimal minBal = input.readDecimal();
                initAmount = initialDeposit();
                try {
                    accountService.savingCreation(initAmount, minBal);
                    System.out.println("Account successfully created!");
                } catch(SQLException e) {
                    System.out.println("Something Went wrong. Please Try again.");
                }
                return true;
            case CURRENT:
                System.out.println("Enter an overdraft limit:");
                BigDecimal overdraftLimit = input.readDecimal();
                initAmount = initialDeposit();
                try {
                    accountService.currentCreation(initAmount, overdraftLimit);
                    System.out.println("Account successfully created!");
                } catch(SQLException e) {
                    System.out.println("Something Went wrong. Please Try again.");
                }
                return true;
            case DEPOSIT:
                int termMonths = maturityMonths();
                LocalDate maturityDate = LocalDate.now().plusMonths(termMonths);
                initAmount = initialDeposit();
                String prompt = "Choose an account to fund your deposit";
                Account fundingAccount = chooseAccount(prompt);
                try {
                    accountService.depositCreation(initAmount, termMonths, maturityDate, fundingAccount);
                    System.out.println("Account successfully created!");
                } catch(SQLException e) {
                    System.out.println("Something Went wrong. Please Try again.");
                }
                return true;
            case LOAN:
                System.out.println("\nHow much money would you like to borrow:");
                BigDecimal principal = input.readDecimal();
                try {
                    accountService.loanCreation(principal);
                    System.out.println("Account successfully created!");
                } catch(SQLException e) {
                    System.out.println("Something Went wrong. Please Try again.");
                }
                return true;
            default:
                System.out.println("ERROR: something went wrong");
                return false;
        }
    }

    public int maturityMonths() {
        System.out.println("Select a maturity date: \n1. 3 month \n2. 6 months \n3. 12 months");
        int choice = input.readInt();

        while(true) {
            switch (choice) {
                case 1:
                    return 3;
                case 2:
                    return 6;
                case 3:
                    return 12;
                default:
                    System.out.println("\nEnter a valid selection");
            }
        }
    }

    public BigDecimal initialDeposit() {
        System.out.println("Deposit an initial amount:");
        return input.readDecimal();
    }

    public List<Account> listAccounts(boolean includeDeposit, boolean isLoan) {
        int count = 1;
        try {
            List<Account> accounts = accountService.getAllAccounts(includeDeposit, isLoan);
            System.out.println("--------------- Accounts ---------------");
            if (accounts.isEmpty()) {
                System.out.println("You have no accounts.");
                loadMenu();
            }
            for(Account account: accounts) {
                System.out.println("| " + count + " | " + account.getAccountNumber() + " | " + account.getAccountType() + " | " + account.getBalance() + " |");

                count++;
            }

            return accounts;
        } catch (SQLException e) {
        System.out.println("Unable to retrieve accounts.");
        loadMenu();
        return null;
        }
    }

    public Account chooseAccount(String prompt) {
        int choice;
        List<Account> accounts = listAccounts(false, false);
            
            if(accounts.size() > 1) {
                System.out.println(prompt + " 1-" + accounts.size());
            } else {
                System.out.println(prompt);
            }

            while (true) {
                choice = input.readInt();

                if (choice >= 1 && choice <= accounts.size()) {
                    break;
                }

                System.out.println("Invalid selection. Choose between 1 and " + accounts.size());
            }

            Account selectedAccount = accounts.get( choice-1);
            return selectedAccount;
    }
}

package com.sam.bank.app;

import java.sql.SQLException;

import com.sam.bank.enums.Navigation;
import com.sam.bank.service.AuthService;
import com.sam.bank.validation.AccountValidator;

public class LoginMenu {
    private ConsoleInput input;
    private AuthService authService;

    public LoginMenu(ConsoleInput input, AuthService authService) {
        this.input = input;
        this.authService = authService;
    }

    public Navigation loadMenu() {
        System.out.println("Welcome to BMS. Enter one of the following selections to continue.");
        System.out.println(" 1. Login\n 2. Register\n 3. Quit");

        int choice = input.readInt();
        Navigation navType = handleInput(choice);
        return navType;
    }

    private Navigation handleInput(int choice) {
        switch (choice) {
            case 1:
                login();
                return Navigation.LOGIN;
            case 2:
                register();
                return Navigation.CONTINUE;
            case 3:
                System.out.println("Goodbye Now");
                return Navigation.QUIT;
            default:
                System.out.println("Invalid Choice!!! Input must be a number 1-3.\n");
                loadMenu();
                return Navigation.CONTINUE;
        }
    }

    private void login() {
        System.out.println("Login \n");
        System.out.println("Enter each field accordingly. \n");

        System.out.println("\nEmail:");
        String email = input.readString();

        System.out.println("\nPassword:");
        String password = input.readPassword();
        try {
            authService.loginCredentials(email, password);
        } catch(SQLException e) {
            System.out.println("\nERROR: COULD NOT SIGN IN. TRY AGAIN WITH CORRECT CREDENTIALS. " + e);
        }
    }

    private void register() {
        System.out.println("Register");
        System.out.println("Enter each field accordingly. \n");

        System.out.println("First name:");
        String firstName = input.readString();

        System.out.println("\nLast name:");
        String lastName = input.readString();

        System.out.println("\nemail:");
        String email = input.readString();

        boolean isValidPhone = false;
        String phone = "";
        int error = 0;
        while(!isValidPhone){
            String errorMessage = error == 0 ? "" : "\n****ERROR: PHONE NUMBERS MUST BE 10 VALID DIGITS****";
            System.out.println(errorMessage);
            System.out.println("Phone number:");
            phone = input.readString();
            isValidPhone = AccountValidator.verifyPhone(phone);
            error = 1;
        }

        System.out.println("\nUsername:");
        String username = input.readString();

        boolean isValidPassword = false;
        String password = "";
        String verifyPassword = "";
        error = 0;
        while(!isValidPassword) {
            String errorMessage = error == 0 ? "\n" : "\n****ERROR: PASSWORDS DONT MATCH****";
            System.out.println(errorMessage);
            System.out.println("Password:");
            password = input.readPassword();
            System.out.println("\nVerify password:");
            verifyPassword = input.readPassword();
            isValidPassword = AccountValidator.verifyPasswordValid(password, verifyPassword);
            error = 1;
        }

        try {
            authService.registerCredentials(firstName, lastName, email, phone, username, password, verifyPassword);
            System.out.println("Your account has been created! Login to start banking!");
        } catch(SQLException e) {
            System.out.println("ERROR: ACCOUNT COULD NOT BE CREATED. RETRY AND VERIFY ALL INFORMATION IS CORRECT AND MATCHES CRITERIAS. " + e);
        }
    }
}

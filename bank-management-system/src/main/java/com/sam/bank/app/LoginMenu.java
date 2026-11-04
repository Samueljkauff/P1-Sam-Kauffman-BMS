package com.sam.bank.app;

import com.sam.bank.enums.Navigation;
import com.sam.bank.service.AuthService;

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

        System.out.println("Email:\n");
        String email = input.readString();

        System.out.println("Password: \n");
        String password = input.readPassword();

        authService.loginCredentials(email, password);
    }

    private void register() {
        System.out.println("Register \n");
        System.out.println("Enter each field accordingly. \n");
        System.out.println("First name: \n");
        String firstName = input.readString();
        System.out.println("Last name: \n");
        String lastName = input.readString();
        System.out.println("email: \n");
        String email = input.readString();
        System.out.println("Phone number: \n");
        String phone = input.readString();
        System.out.println("Username: \n");
        String username = input.readString();
        System.out.println("Password: \n");
        String password = input.readPassword();
        System.out.println("Verify password: \n");
        String verifyPassword = input.readPassword();

        authService.registerCredentials(firstName, lastName, email, phone, username, password, verifyPassword);
    }
}

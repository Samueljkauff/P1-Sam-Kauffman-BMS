package com.sam.bank.app;

public class LoginMenu {
    private ConsoleInput input;

    public LoginMenu(ConsoleInput input) {
        this.input = input;
    }

    public void loadMenu() {
        System.out.println("Welcome to BMS. Enter one of the following selections to continue.");
        System.out.println(" 1. Login\n 2. Register\n 3. Quit");
        int choice = input.readInt();
        handleInput(choice);
    }

    private void handleInput(int choice) {

    }
}

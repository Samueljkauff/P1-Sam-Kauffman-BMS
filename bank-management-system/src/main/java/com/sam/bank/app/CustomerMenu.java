package com.sam.bank.app;

public class CustomerMenu {
    private ConsoleInput input;

    public CustomerMenu(ConsoleInput input) {
        this.input = input;
    }

    public void loadMenu() {
        System.out.println("Enter the following selections to continue.");
        System.out.println(" 1. Deposit\n 2. Withdrawl \n 3. Transfer \n 4. Balance \n 5. History \n 6. Accounts \n 7. Profile \n 8. Quit");
        int choice = input.readInt();
        handleInput(choice);

    }

    private void handleInput(int choice) {
        
    }
}

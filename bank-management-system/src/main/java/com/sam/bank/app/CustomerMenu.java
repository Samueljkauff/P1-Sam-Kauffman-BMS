package com.sam.bank.app;

import com.sam.bank.enums.Navigation;

public class CustomerMenu {
    private ConsoleInput input;

    public CustomerMenu(ConsoleInput input) {
        this.input = input;
    }

    public Navigation loadMenu() {
        System.out.println("Enter the following selections to continue.");
        System.out.println(" 1. Deposit\n 2. Withdrawl \n 3. Transfer \n 4. Balance \n 5. History \n 6. Accounts \n 7. Profile \n 8. Logout \n 9. Quit");

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

                return Navigation.CONTINUE;
            case 7:

                return Navigation.CONTINUE;
            case 8:

                return Navigation.LOGOUT;
            case 9:
                System.out.println("Goodbye Now");
                return Navigation.QUIT;
            default:
                System.out.println("Invalid Choice!!! Input must be a number 1-8.\n");
                loadMenu();
                return Navigation.CONTINUE;
        }
    }
}

package com.sam.bank.app;

import java.io.Console;
import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleInput {
    private final Scanner scan;
    private final Console hiddenScan;

    public ConsoleInput() {
        this.scan = new Scanner(System.in);
        this.hiddenScan = System.console();
    }
    public int readInt() {
        while (true) {
            try {
                String value = scan.nextLine().trim();
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    public BigDecimal readDecimal() {
        while (true) {
            try {
                String value = scan.nextLine().trim();
                return new BigDecimal(value);
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Please enter a valid number.");
            }
        }
    }

    public String readString() {
        return scan.nextLine().trim();
    }

    public String readPassword() {

        char[] password = hiddenScan.readPassword();

        return new String(password);
    }
}

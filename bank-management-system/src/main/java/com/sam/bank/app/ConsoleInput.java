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
            try {
                String value = scan.nextLine();
                return Integer.parseInt(value.trim());

            } catch (NumberFormatException e) {
                return 0;
            }
    }

    public BigDecimal readDecimal() {
        try {
            String value = scan.nextLine().trim();
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Please enter a valid number.");
            return readDecimal();
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

package com.sam.bank.app;

import java.io.Console;
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

    public String readString() {
        return scan.nextLine().trim();
    }

    public String readPassword() {

        char[] password = hiddenScan.readPassword();

        return new String(password);
    }
}

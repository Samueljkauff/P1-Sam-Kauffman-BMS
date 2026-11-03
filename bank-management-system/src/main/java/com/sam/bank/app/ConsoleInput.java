package com.sam.bank.app;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleInput {
    private final Scanner scan;

    public ConsoleInput() {
        this.scan = new Scanner(System.in);
    }
    public int readInt() {
        while (true) {
            try {
                String value = scan.nextLine();
                return Integer.parseInt(value.trim());

            } catch (NumberFormatException e) {
                return 0;
            }
        }
    }
}

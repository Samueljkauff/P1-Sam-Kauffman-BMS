package com.sam.bank.app;

import java.util.Scanner;

public class ConsoleInput {
    private final Scanner scan;

    public ConsoleInput() {
        this.scan = new Scanner(System.in);
    }
    public int readInt() {
        return scan.nextInt();
    }
}

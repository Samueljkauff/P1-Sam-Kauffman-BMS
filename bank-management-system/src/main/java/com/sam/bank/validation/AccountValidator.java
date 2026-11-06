package com.sam.bank.validation;

public class AccountValidator {

    public static boolean verifyPasswordValid(String password, String verifyPassword) {
        if(password.equals(verifyPassword)) {
            return true;
        }

        return false;
    }
    
    public static boolean verifyPhone(String phone) {
        if(phone.length() != 10) {
            return false;
        }

        char[] numbers = phone.toCharArray();

        for(char c : numbers) {
            if(!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }
}

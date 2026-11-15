package com.sam.bank.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountValidator {
    // At least 8 chars, 1 digit, 1 lower, 1 upper, 1 special char, strictly NO spaces
private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[.#?!@$%^&*-])[A-Za-z0-9.#?!@$%^&*-]{8,}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public static boolean verifyPasswordValid(String password, String verifyPassword) {
        Matcher matcher = pattern.matcher(password);
        boolean regexValid = matcher.matches();
        boolean matches = password.equals(verifyPassword);

        if(matches && regexValid) {
            return true;
        }
        String error = regexValid ? "Passwords must match."  : "Password be 8 characters with a digit, a lowercase, an uppercase, and a special character. NO SPACES ALLOWED.";
        System.out.println(error);
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

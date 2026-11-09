package com.sam.bank.security;

public interface PasswordHasher {
    String hashPassword(String pasword);

    boolean verifyPassword(String password, String hashedPassword);
}

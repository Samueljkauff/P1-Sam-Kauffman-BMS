package com.sam.bank.security;

public interface PasswordHasher {
    String hashPassword(String pasword);

    String verifyPassword(String password, String hashedPassword);
}

package com.sam.bank.security;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptPasswordHasher implements PasswordHasher {
    @Override
    public String hashPassword(String pasword) {
        String hashedPassword = BCrypt.hashpw(pasword, BCrypt.gensalt());
        return hashedPassword;
    }

    @Override
    public String verifyPassword(String password, String hashedPassword) {
        // TODO Auto-generated method stub
        return null;
    }
}

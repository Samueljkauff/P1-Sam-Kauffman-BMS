package com.sam.bank.exception;

public class SessionExpiredException extends RuntimeException {
        public SessionExpiredException() {
        super("Session expired due to inactivity.");
    }
}

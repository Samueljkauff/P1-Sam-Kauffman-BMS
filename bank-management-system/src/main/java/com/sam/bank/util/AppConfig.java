package com.sam.bank.util;

public class AppConfig {
    private String URL;
    private String username;
    private String password;

    public AppConfig() {
        this.URL = retrieveURL();
        this.username = retrieveUsername();
        this.password = retrievePassword();
    }

    public String getURL() {
        return this.URL;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    private String retrieveURL() {
        String url = System.getenv("DB_URL");

        if (url == null || url.isBlank()) {
            throw new IllegalStateException("Missing required environment variable: DB_URL");
        }

        return url;
    }

    private String retrieveUsername() {
        String url = System.getenv("DB_USERNAME");

        if (url == null || url.isBlank()) {
            throw new IllegalStateException("Missing required environment variable: DB_URL");
        }

        return url;
    }

    private String retrievePassword() {
        String url = System.getenv("DB_PASSWORD");

        if (url == null || url.isBlank()) {
            throw new IllegalStateException("Missing required environment variable: DB_URL");
        }

        return url;
    }
}

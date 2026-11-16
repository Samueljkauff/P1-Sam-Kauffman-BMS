package com.sam.bank.util;

import io.github.cdimascio.dotenv.Dotenv;

public class AppConfig {
    private Dotenv dotenv;
    private String URL;
    private String username;
    private String password;

    public AppConfig() {
        this.dotenv = Dotenv.configure()
            .directory(".")
            .filename(".env")
            .load();
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
        String url = dotenv.get("DB_URL");

        if (url == null || url.isBlank()) {
            throw new IllegalStateException("Missing required environment variable: DB_URL");
        }

        return url;
    }

    private String retrieveUsername() {
        String url = dotenv.get("DB_USERNAME");

        if (url == null || url.isBlank()) {
            throw new IllegalStateException("Missing required environment variable: DB_URL");
        }

        return url;
    }

    private String retrievePassword() {
        String url = dotenv.get("DB_PASSWORD");

        if (url == null || url.isBlank()) {
            throw new IllegalStateException("Missing required environment variable: DB_URL");
        }

        return url;
    }
}

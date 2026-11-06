package com.sam.bank.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private final AppConfig app;

    public ConnectionFactory(AppConfig app) {
        this.app = app;
    }
    
    public Connection getConnection() throws SQLException {
        String url = app.getURL();
        String username = app.getUsername();
        String password = app.getPassword();

        if (url == null || url.isBlank() || username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new IllegalStateException("Missing database configuration.");
        }
        
        Connection connection = DriverManager.getConnection(url, username, password);

        return connection;
    }
}

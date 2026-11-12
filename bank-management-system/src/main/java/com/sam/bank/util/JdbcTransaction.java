package com.sam.bank.util;

import java.sql.Connection;
import java.sql.SQLException;

import com.sam.bank.interfaces.SqlOperation;

public class JdbcTransaction {
    private final ConnectionFactory connectionFactory;

    public JdbcTransaction(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void execute(SqlOperation operation) throws SQLException {
        try(Connection connection = connectionFactory.getConnection();) {
            connection.setAutoCommit(false);
            try {
                operation.execute(connection);
                connection.commit();
            } catch(SQLException e) {
                System.out.println(e);
                connection.rollback();
                throw e;
            }
        }
    }
}

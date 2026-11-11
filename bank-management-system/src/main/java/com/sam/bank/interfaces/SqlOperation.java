package com.sam.bank.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlOperation {
    void execute(Connection connection) throws SQLException;
}

package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    private static final String connectionURL = "jdbc:mysql://localhost:3306/mysql";
    private static final String passwordUser = "Matrix%1989";
    private static final String userName = "root";
    private static Connection newConnection;

    public Util() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try {
            newConnection = DriverManager.getConnection(connectionURL, userName, passwordUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return newConnection;
    }
}


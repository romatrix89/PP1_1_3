package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    private static final String connectionURL = "jdbc:mysql://localhost:3306/mysql";
    private static final String passwordUser = "Matrix%1989";
    private static final String userName = "root";
    private static final Connection newConnection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            newConnection = DriverManager.getConnection(connectionURL, userName, passwordUser);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return newConnection;
    }
}

package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Database credentials and connection string
    private static final String URL = "jdbc:mysql://localhost:3306/sunrise_dental";
    private static final String USER = "root";

    // IMPORTANT: Replace "password" with the actual password you created during MySQL installation
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        // Load the MySQL JDBC driver explicitly (required for Tomcat web applications)
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Establish and return the connection
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
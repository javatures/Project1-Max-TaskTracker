package TaskTracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnection 
{
    private final String url = "jdbc:postgresql://localhost:5432/project1";
    private final String username = "project1";
    private final String password = "password";
    private static Connection connection;

    private SqlConnection()
    {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static SqlConnection instance = new SqlConnection();

    public static SqlConnection getInstance()
    {
        return instance;
    }

    public Connection getConnection()
    {
        return connection;
    }
}

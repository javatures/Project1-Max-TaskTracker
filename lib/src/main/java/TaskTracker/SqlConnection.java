package TaskTracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnection 
{
    private static SqlConnection instance = new SqlConnection();

    private static final String url = "jdbc:postgresql://localhost:5432/project1";
    private static final String username = "project1";
    private static final String password = "password";
    private Connection connection;

    private SqlConnection()
    {
        try {
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static SqlConnection getInstance()
    {
        return instance;
    }

    public Connection getConnection()
    {
        return connection;
    }
}

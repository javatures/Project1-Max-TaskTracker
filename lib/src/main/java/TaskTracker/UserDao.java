package TaskTracker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class UserDao 
{
    private Connection connection;

    public UserDao()
    {
        connection = SqlConnection.getInstance().getConnection();
    }

    public void insert(User user)
    {
        try 
        {
            PreparedStatement pStatement = connection.prepareStatement("insert into users (userType, userName, userPassword, fname, lname, manager) values (?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);

            pStatement.setInt(1, user.getUserType());
            pStatement.setString(2, user.getUserName());
            pStatement.setString(3, user.getUserPassword());
            pStatement.setString(4, user.getFname());
            pStatement.setString(5, user.getLname());
            pStatement.setInt(6, user.getManager());

            pStatement.executeUpdate();
            ResultSet rSet = pStatement.getGeneratedKeys();
            if(rSet.next())
            {
                user.setUserId((int)rSet.getLong(1));
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User validateLogin(String userName, String password)
    {
        User foundUser = null;
        try 
        {
            PreparedStatement pStatement = connection.prepareStatement("select * from users where userName = ? and userPassword = ?;");

            pStatement.setString(1, userName);
            pStatement.setString(2, password);

            ResultSet rSet = pStatement.executeQuery();
            rSet.next();
            int userId = rSet.getInt("userId");
            int userType = rSet.getInt("userType");
            String uName = rSet.getString("userName");
            String userPassword = rSet.getString("userPassword");
            String fname = rSet.getString("fname");
            String lname = rSet.getString("lname");
            int manager = rSet.getInt("manager");

            foundUser = new User(userId, userType, uName, userPassword, fname, lname, manager);
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }

        return foundUser;
    }

    public User findUserByName(String userName)
    {
        User foundUser = null;
        try 
        {
            PreparedStatement pStatement = connection.prepareStatement("select * from users where userName = ?;");

            pStatement.setString(1, userName);

            ResultSet rSet = pStatement.executeQuery();
            rSet.next();
            int userId = rSet.getInt("userId");
            int userType = rSet.getInt("userType");
            String uName = rSet.getString("userName");
            String userPassword = rSet.getString("userPassword");
            String fname = rSet.getString("fname");
            String lname = rSet.getString("lname");
            int manager = rSet.getInt("manager");

            foundUser = new User(userId, userType, uName, userPassword, fname, lname, manager);
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }

        return foundUser;
    }

    public User findUserById(int userId)
    {
        User foundUser = null;
        try 
        {
            PreparedStatement pStatement = connection.prepareStatement("select * from users where userId = ?;");

            pStatement.setInt(1, userId);

            ResultSet rSet = pStatement.executeQuery();
            rSet.next();
            int uId = rSet.getInt("userId");
            int userType = rSet.getInt("userType");
            String uName = rSet.getString("userName");
            String userPassword = rSet.getString("userPassword");
            String fname = rSet.getString("fname");
            String lname = rSet.getString("lname");
            int manager = rSet.getInt("manager");

            foundUser = new User(uId, userType, uName, userPassword, fname, lname, manager);
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }

        return foundUser;
    }

    public void updateUser(User user)
    {
        try 
        {
            PreparedStatement pStatement = connection.prepareStatement("update users set userName = ?, userPassword = ?, fname = ?, lname = ? where userId = ?;");

            pStatement.setString(1, user.getUserName());
            pStatement.setString(2, user.getUserPassword());
            pStatement.setString(3, user.getFname());
            pStatement.setString(4, user.getLname());
            pStatement.setInt(5, user.getUserId());

            pStatement.executeUpdate();
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, User> getAll()
    {
        Map<Integer, User> users = new HashMap<>();
        try 
        {
            PreparedStatement pStatement = connection.prepareStatement("SELECT * FROM users;");
            ResultSet rSet = pStatement.executeQuery();

            while(rSet.next())
            {
                int uId = rSet.getInt("userId");
                int userType = rSet.getInt("userType");
                String uName = rSet.getString("userName");
                String userPassword = rSet.getString("userPassword");
                String fname = rSet.getString("fname");
                String lname = rSet.getString("lname");
                int manager = rSet.getInt("manager");

                users.put(uId, new User(uId, userType, uName, userPassword, fname, lname, manager));
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public Map<Integer, User> getAllOfUserType(int uType)
    {
        Map<Integer, User> users = new HashMap<>();
        try 
        {
            PreparedStatement pStatement = connection.prepareStatement("SELECT * FROM users WHERE userType = ?;");
            pStatement.setInt(1, uType);
            ResultSet rSet = pStatement.executeQuery();

            while(rSet.next())
            {
                int uId = rSet.getInt("userId");
                int userType = rSet.getInt("userType");
                String uName = rSet.getString("userName");
                String userPassword = rSet.getString("userPassword");
                String fname = rSet.getString("fname");
                String lname = rSet.getString("lname");
                int manager = rSet.getInt("manager");

                users.put(uId, new User(uId, userType, uName, userPassword, fname, lname, manager));
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}

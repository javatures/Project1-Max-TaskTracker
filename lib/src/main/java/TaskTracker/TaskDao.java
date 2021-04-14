package TaskTracker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TaskDao 
{
    private Connection connection = SqlConnection.getInstance().getConnection();
    
    public void insert(Task task)
    {
        try 
        {
            PreparedStatement pStatement = connection.prepareStatement("insert into tasks (assigner, reciever, title, body, currentStatus, evidenceLocation) values (?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);

            pStatement.setInt(1, task.getAssigner());
            pStatement.setInt(2, task.getReciever());
            pStatement.setString(3, task.getTitle());
            pStatement.setString(4, task.getBody());
            pStatement.setInt(5, task.getCurrentStatus());
            pStatement.setString(6, task.getEvidenceLocation());

            pStatement.executeUpdate();
            ResultSet rSet = pStatement.getGeneratedKeys();
            if(rSet.next())
            {
                task.setTaskId((int)rSet.getLong(1));
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
}

package TaskTracker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * For managers to get all the tasks they have assigned
     * @param userId
     * @return
     */
    public List<Task> getAllAssignedTasks(int userId)
    {
        List<Task> tasks = new ArrayList<>();
        try 
        {
            PreparedStatement pStatement = connection.prepareStatement("SELECT * FROM tasks LEFT JOIN users ON tasks.assigner = users.userId WHERE assigner = ?;");
            pStatement.setInt(1, userId);
            ResultSet rSet = pStatement.executeQuery();

            while(rSet.next())
            {
                int taskId = rSet.getInt("taskId");
                int assigner = rSet.getInt("assigner");
                int reciever = rSet.getInt("reciever");
                String title = rSet.getString("title");
                String body = rSet.getString("body");
                int currentStatus = rSet.getInt("currentStatus");
                String evidenceLocation = rSet.getString("evidenceLocation");

                tasks.add(new Task(taskId, assigner, reciever, title, body, currentStatus, evidenceLocation));
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    /**
     * For employees to get all tasks they have recieved
     * @param userId
     * @return
     */
    public List<Task> getAllRecievedTasks(int userId)
    {
        List<Task> tasks = new ArrayList<>();
        try 
        {
            PreparedStatement pStatement = connection.prepareStatement("SELECT * FROM tasks WHERE reciever = ?;");
            pStatement.setInt(1, userId);
            ResultSet rSet = pStatement.executeQuery();

            while(rSet.next())
            {
                int taskId = rSet.getInt("taskId");
                int assigner = rSet.getInt("assigner");
                int reciever = rSet.getInt("reciever");
                String title = rSet.getString("title");
                String body = rSet.getString("body");
                int currentStatus = rSet.getInt("currentStatus");
                String evidenceLocation = rSet.getString("evidenceLocation");

                tasks.add(new Task(taskId, assigner, reciever, title, body, currentStatus, evidenceLocation));
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    public Map<Integer, Task> getAll()
    {
        Map<Integer, Task> tasks = new HashMap<>();
        try 
        {
            PreparedStatement pStatement = connection.prepareStatement("SELECT * FROM tasks;");
            ResultSet rSet = pStatement.executeQuery();

            while(rSet.next())
            {
                int taskId = rSet.getInt("taskId");
                int assigner = rSet.getInt("assigner");
                int reciever = rSet.getInt("reciever");
                String title = rSet.getString("title");
                String body = rSet.getString("body");
                int currentStatus = rSet.getInt("currentStatus");
                String evidenceLocation = rSet.getString("evidenceLocation");

                tasks.put(taskId, new Task(taskId, assigner, reciever, title, body, currentStatus, evidenceLocation));
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    public void changeStatus(int taskId, int status)
    {
        try 
        {
            PreparedStatement pStatement = connection.prepareStatement("UPDATE tasks SET currentStatus = ? WHERE taskId = ?");
            pStatement.setInt(1, status);
            pStatement.setInt(2, taskId);

            pStatement.executeUpdate();
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

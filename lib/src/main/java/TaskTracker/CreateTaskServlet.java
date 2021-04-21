package TaskTracker;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/createTask")
public class CreateTaskServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private TaskDao tDao = new TaskDao();

    private int getUserId(HttpServletRequest req, String userType)
    {
        int currentUserId = 0;
        for(Cookie i : req.getCookies())
        {
            if(i.getName().equals(userType))
            {
                currentUserId = Integer.parseInt(i.getValue());
            }
        }
        return currentUserId;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        RequestDispatcher view = req.getRequestDispatcher("createTask.html");
        view.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        int assigner = getUserId(req, "currentUser");
        int reciever = getUserId(req, "reciever");
        Task createdTask = new Task(assigner, reciever, req.getParameter("title"), req.getParameter("body"), 1);
        tDao.insert(createdTask);

        if(createdTask != null)
        {
            resp.sendRedirect(req.getContextPath() + "/homepage");
        }

        doGet(req, resp); 
    }
}
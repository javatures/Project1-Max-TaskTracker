package TaskTracker;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/managerDenyTask")
public class ManagerDenyTask extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private TaskDao tDao = new TaskDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        int taskId = Integer.parseInt(req.getParameter("denyTask"));
        tDao.changeStatus(taskId, 4);

        resp.sendRedirect(req.getContextPath() + "/homepage");
    }
}

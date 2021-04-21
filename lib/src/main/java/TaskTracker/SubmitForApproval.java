package TaskTracker;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/submitForApproval")
public class SubmitForApproval extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private TaskDao tDao = new TaskDao();

    private int getTaskId(HttpServletRequest req)
    {
        int taskId = 0;
        for(Cookie i : req.getCookies())
        {
            if(i.getName().equals("idOfSubmission"))
            {
                taskId = Integer.parseInt(i.getValue());
            }
        }
        return taskId;
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        int taskId = getTaskId(req);
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
		  
	    out.println("<input hidden id=\"taskId\" type=\"text\" value=\""+taskId+"\" />");

        req.getRequestDispatcher("submitForApproval.html").include(req, resp);
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        int taskId = getTaskId(req);
        tDao.changeStatus(taskId, 2);

        resp.sendRedirect(req.getContextPath() + "/homepage");
    }
}
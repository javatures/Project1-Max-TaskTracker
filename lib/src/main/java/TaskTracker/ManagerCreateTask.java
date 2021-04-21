package TaskTracker;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/managerCreateTask")
public class ManagerCreateTask extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        String reciever = req.getParameter("createTask");
        Cookie cookie = new Cookie("reciever", reciever);
        resp.addCookie(cookie);

        resp.sendRedirect(req.getContextPath() + "/createTask");
    }
}

package TaskTracker;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/homepage")
public class HomepageServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private UserDao uDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        int currentUserId = 0;
        for(Cookie i : req.getCookies())
        {
            if(i.getName().equals("currentUser"))
            {
                currentUserId = Integer.parseInt(i.getValue());
            }
        }
        User currentUser = uDao.findUserById(currentUserId);

        if(currentUser.getUserType() == 1)
        {
            RequestDispatcher view = req.getRequestDispatcher("managerHomepage.html");
            view.forward(req, resp);
        }
        else if(currentUser.getUserType() == 2)
        {
            RequestDispatcher view = req.getRequestDispatcher("employeeHomepage.html");
            view.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {

    }
}

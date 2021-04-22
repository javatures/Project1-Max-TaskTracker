package TaskTracker;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet("/login")
public class LoginServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private UserDao uDao = new UserDao();
    private static final Logger log = LogManager.getLogger(LoginServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        RequestDispatcher view = req.getRequestDispatcher("login.html");
        view.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        User attempedUserLogin = uDao.validateLogin(req.getParameter("username"), req.getParameter("password"));

        if(attempedUserLogin != null)
        {
            Cookie cookie = new Cookie("currentUser", attempedUserLogin.getUserId()+"");
            resp.addCookie(cookie);

            resp.sendRedirect(req.getContextPath() + "/homepage");
        }
        else{
            doGet(req, resp);
        }
    }
}
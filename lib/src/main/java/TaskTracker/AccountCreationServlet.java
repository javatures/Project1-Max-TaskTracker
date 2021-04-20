package TaskTracker;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/newAccount")
public class AccountCreationServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private UserDao uDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        RequestDispatcher view = req.getRequestDispatcher("newAccount.html");
        view.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        User attempedUserCreation = new User(Integer.parseInt(req.getParameter("userType")), req.getParameter("username"), req.getParameter("password"), req.getParameter("fname"), req.getParameter("lname"), Integer.parseInt(req.getParameter("manager")));
        uDao.insert(attempedUserCreation);

        HttpSession session=req.getSession();  
        session.setAttribute("currentUser",attempedUserCreation);  

        Cookie cookie = new Cookie("currentUserId", ""+attempedUserCreation.getUserId());
        resp.addCookie(cookie);

        resp.sendRedirect(req.getContextPath() + "/homepage");
    }
}

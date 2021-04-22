package TaskTracker;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/viewAccount")
public class AccountInformation extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private UserDao uDao = new UserDao();

    private User getCurrentUser(HttpServletRequest req)
    {
        int currentUserId = 0;
        for(Cookie i : req.getCookies())
        {
            if(i.getName().equals("currentUser"))
            {
                currentUserId = Integer.parseInt(i.getValue());
            }
        }
        return uDao.findUserById(currentUserId);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        User currentUser = getCurrentUser(req);
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
		  
	    out.println("<input hidden id=\"userId\" type=\"text\" value=\""+currentUser.getUserId()+"\" />");
        
        req.getRequestDispatcher("viewAccount.html").include(req, resp);;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        User currentUser = getCurrentUser(req);
        if(!req.getParameter("username").equals(""))
        {
            currentUser.setUserName(req.getParameter("username"));
        }
        
        if(!req.getParameter("password").equals(""))
        {
            currentUser.setUserPassword(req.getParameter("password"));
        }
        
        if(!req.getParameter("fname").equals(""))
        {
            currentUser.setFname(req.getParameter("fname"));
        }

        if(!req.getParameter("lname").equals(""))
        {
            currentUser.setLname(req.getParameter("lname"));
        }

        uDao.updateUser(currentUser);  

        resp.sendRedirect(req.getContextPath() + "/homepage");
    }
}

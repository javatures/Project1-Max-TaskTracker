package TaskTracker;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

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

    private void deleteCookie(HttpServletRequest req, String cookieName)
    {
        for(Cookie i : req.getCookies())
        {
            if(i.getName().equals(cookieName))
            {
                i.setMaxAge(0);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        User currentUser = getCurrentUser(req);
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
		  
	    out.println("<input hidden id=\"userId\" type=\"text\" value=\""+currentUser.getUserId()+"\" />");

        if(currentUser.getUserType() == 1)
        {
            req.getRequestDispatcher("managerHomepage.html").include(req, resp);
            out.close();
        }
        else if(currentUser.getUserType() == 2)
        {
            req.getRequestDispatcher("employeeHomepage.html").include(req, resp);
            out.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String logout = req.getParameter("logout");

        if(logout.equals("true"))
        {
            ArrayList<String> cookiesToKill = new ArrayList<>();
            cookiesToKill.add("currentUser");
            cookiesToKill.add("reciever");

            for(String cookieName : cookiesToKill)
            {
                deleteCookie(req, cookieName);
            }
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}

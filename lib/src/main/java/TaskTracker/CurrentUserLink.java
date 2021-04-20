package TaskTracker;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/currentuser")
public class CurrentUserLink extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private ObjectMapper mapper = new ObjectMapper();
    private UserDao uDao = new UserDao();

    private void sendAsJson(HttpServletResponse response, Object obj) throws IOException {
		
		response.setContentType("application/json");
		
		String res = mapper.writeValueAsString(obj);
		     
		PrintWriter out = response.getWriter();
		  
		out.print(res);
		out.flush();
	}

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
        sendAsJson(resp, currentUser);
        return;
    }
}

package TaskTracker;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/api/users/*")
public class UserApiServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private UserDao uDao = new UserDao();
    private ObjectMapper mapper = new ObjectMapper();

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
        String pathInfo = req.getPathInfo();
        Map<Integer, User> mappedUsers = uDao.getAll();
        Collection<User> users = mappedUsers.values();

		if(pathInfo == null || pathInfo.equals("/"))
        {			
			sendAsJson(resp, users);
			return;
		}

		String[] splits = pathInfo.split("/");
		
		if(splits.length != 2) 
        {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		String stringModelId = splits[1];
        int modelId = Integer.parseInt(stringModelId);
		
		if(!mappedUsers.containsKey(modelId)) 
        {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		sendAsJson(resp, mappedUsers.get(modelId));
		return;
    }
}

package TaskTracker;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/api/tasks/manager/*")
public class TaskApiManagerServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private TaskDao tDao = new TaskDao();
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
        Map<Integer, User> mappedManagers = uDao.getAllOfUserType(1);

		if(pathInfo == null || pathInfo.equals("/"))
        {			
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
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
		
		if(!mappedManagers.containsKey(modelId)) 
        {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		sendAsJson(resp, tDao.getAllAssignedTasks(modelId));
		return;
    }
}

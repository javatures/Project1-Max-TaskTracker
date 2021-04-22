package TaskTracker;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/viewHistory/*")
public class ViewHistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDao uDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        Map<Integer, User> mappedUsers = uDao.getAll();

        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] splits = pathInfo.split("/");

        if (splits.length != 2) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String stringModelId = splits[1];
        int modelId = Integer.parseInt(stringModelId);

        if (!mappedUsers.containsKey(modelId)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if (mappedUsers.get(modelId).getUserType() == 1) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<input hidden id=\"userId\" type=\"text\" value=\"" + modelId + "\" />");

        req.getRequestDispatcher("../viewHistory.html").include(req, resp);
    }
}

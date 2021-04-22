package TaskTracker;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/submitForApproval")
@MultipartConfig (
    location = "uploads"
)
public class SubmitForApproval extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TaskDao tDao = new TaskDao();

    private int getTaskId(HttpServletRequest req) {
        int taskId = 0;
        for (Cookie i : req.getCookies()) {
            if (i.getName().equals("idOfSubmission")) {
                taskId = Integer.parseInt(i.getValue());
            }
        }
        return taskId;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int taskId = getTaskId(req);
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<input hidden id=\"taskId\" type=\"text\" value=\"" + taskId + "\" />");

        req.getRequestDispatcher("submitForApproval.html").include(req, resp);
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<p></p>");
        int taskId = getTaskId(req);

        List<Part> parts = req.getParts().stream().filter(part -> part.getSubmittedFileName() != null && part.getName().equals("file")).collect(Collectors.toList());
        if(!parts.get(0).getSubmittedFileName().equals(""))
        {
            String fileName = parts.get(0).getSubmittedFileName();

            File fileSaveDir = new File("/Users/ML/VSCodeProjects/TaskTracker/lib/src/main/webapp/uploads");
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdir();
            }

            File file = new File(fileSaveDir, fileName);
            
            try(InputStream input = parts.get(0).getInputStream())
            {
                Files.copy(input, file.toPath());
            }
            tDao.uploadEvidence(taskId, "uploads/"+fileName);
        }

        tDao.changeStatus(taskId, 2);

        resp.sendRedirect(req.getContextPath() + "/homepage");
    }
}
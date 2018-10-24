package servlets;

import com.google.gson.Gson;
import engine.Engine;
import engine.UserManager;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@MultipartConfig
public class LoadGameServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()){
            Gson gson = new Gson();
            Engine engine = ServletUtils.getEngine(getServletContext());
            Part part = request.getPart("file");
            String creator = SessionUtils.getUsername(request);
            String errorMsg = engine.loadXmlFile(part.getInputStream(), creator);
            String json = gson.toJson(errorMsg);
            out.println(json);
            out.flush();
        }
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
package servlets;

import utils.ServletUtils;
import utils.SessionUtils;
import engine.UserManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LoginServlet extends HttpServlet {
    private final String LOBBY_URL = "../pages/lobby.html";
    private final String LOGIN_URL = "../pages/login.html";
    private final String LOGIN_ERROR = "/pages/loginaftererror.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String usernameFromSession = SessionUtils.getUsername(request);
        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        if (usernameFromSession == null) {
            String usernameFromParameter = request.getParameter("username");
            String playerType = request.getParameter("playerType");
            if (userManager.isUserExists(usernameFromParameter)) {
                String errorMessage = "Username " + usernameFromParameter + " already exists. Please enter a different username.";
                request.setAttribute("error", errorMessage);
                getServletContext().getRequestDispatcher(LOGIN_ERROR).forward(request, response);
            } else {
                userManager.addUser(usernameFromParameter, playerType.equals("computer"));
                request.getSession(true).setAttribute("username", usernameFromParameter);
                response.sendRedirect(LOBBY_URL);
            }
        } else {
            response.sendRedirect(LOBBY_URL);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}

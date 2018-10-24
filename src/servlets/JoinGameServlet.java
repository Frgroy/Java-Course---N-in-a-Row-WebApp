package servlets;

import engine.Engine;
import engine.Game;
import engine.UserManager;
import engine.eGameStatus;
import utils.ServletUtils;
import utils.SessionUtils;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JoinGameServlet extends HttpServlet {
    private final String GAME_ROOM_URL = "../pages/gameRoom.html";
    private final String LOBBY_URL = "../pages/lobby.html";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String gameName = request.getParameter("button");
        gameName = gameName.substring(5);
        Engine engine = ServletUtils.getEngine(getServletContext());
        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        Game game = engine.getGameByName(gameName);
        String userName = SessionUtils.getUsername(request);
        Boolean isComputer = userManager.isUserIsComputer(userName);
        if (userManager.isUserIsPlaying(userName)) {
            response.sendRedirect(LOBBY_URL);
        }
        else {
            if (game.hasRoom()) {
                if (game.getStatus() == eGameStatus.NotStarted){
                    engine.addPlayer(game, userName, isComputer);
                    userManager.setUserAsPlaying(userName);
                    response.sendRedirect(GAME_ROOM_URL);
                } else {
                    response.sendRedirect(LOBBY_URL);
                }
            }
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

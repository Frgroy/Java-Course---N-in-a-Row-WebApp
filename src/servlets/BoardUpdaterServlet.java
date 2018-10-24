package servlets;

import com.google.gson.Gson;
import engine.*;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class BoardUpdaterServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            Engine engine = ServletUtils.getEngine(getServletContext());
            Game game = engine.getGameByName(request.getParameter("name"));
            int[][] board = new int[game.getBoard().getRows()][game.getBoard().getCols()];
            for (int i = 0; i < game.getBoard().getRows(); i++) {
                for (int j = 0; j < game.getBoard().getCols(); j++) {
                    if (game.getBoard().getDisc(i, j) == null) {
                        board[i][j] = 0;
                    } else {
                        board[i][j] = game.getBoard().getDisc(i, j).getPlayer().getPlayerColor();
                    }
                }
            }
            eGameStatus status = game.getStatus();
            List<Player> playerList = game.getPlayerList();
            String currentPlayer = game.getCurrentPlayer() == null ? "" : game.getCurrentPlayer().getPlayerName();
            int version = game.getVersion();
            List <PlayerData> playerDataList = new LinkedList<PlayerData>();
            for (Player player : game.getPlayerList()) {
                playerDataList.add(new PlayerData(player));
            }
            GameUpdate updater = new GameUpdate(board, status, playerDataList, currentPlayer, version);
            String json = gson.toJson(updater);
            out.println(json);
            out.flush();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
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

    class GameUpdate {
        private final int[][] board;
        private final eGameStatus status;
        private final List<PlayerData> playerList;
        private final String currentPlayer;
        private final int version;

        public GameUpdate(int[][] board, eGameStatus status, List<PlayerData> playerDataList, String currentPlayer, int version) {
            this.board = board;
            this.status = status;
            this.playerList = playerDataList;
            this.currentPlayer = currentPlayer;
            this.version = version;
        }
    }

    private class PlayerData {
        String playerName;
        String playerMode;
        int numOfPlays;
        int playerColor;

        public PlayerData(Player player) {
            this.playerName = player.getPlayerName();
            this.playerMode = player.getPlayerMode().toString();
            this.numOfPlays = player.getNumOfPlays();
            this.playerColor = player.getPlayerColor();
        }
    }
}

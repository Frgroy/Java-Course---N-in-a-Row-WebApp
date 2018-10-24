package engine;

import java.util.*;

public class GameManager {

    private final Map<String, Game> games;
    private final Map<String, GameData> gamesData;

    public GameManager() {
        gamesData = new HashMap<String, GameData>();
        games = new HashMap<>();
    }

    public void addNewGame(Game game, String creator) {
        games.put(game.getName(), game);
        gamesData.put(game.getName(), new GameData(creator, game));
    }

    public boolean ExecuteTurn(String gameName, String userName, int column, boolean isPopout) {
        Game g = games.get(gameName);
        if (g.getStatus() == eGameStatus.InProgress) {
            if (g.getCurrentPlayer().getPlayerName().equals(userName)) {
                g.ExecuteTurn(column, isPopout);
                while (g.getCurrentPlayer().getPlayerMode() == ePlayerMode.Computer &&
                        g.getStatus() == eGameStatus.InProgress) {
                    g.PlayComputerTurn();
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public Set<GameData> getGamesData() {
        return new HashSet<GameData>(gamesData.values());
    }

    public Game getSpecificGame(String gameName) {
        return games.get(gameName);
    }

    public GameData getSpecificGameData(String gameName) {
        return gamesData.get(gameName);
    }

    public void addPlayer(Game game, String userName, Boolean isComputer) {
        game.addPlayer(userName, isComputer);
        gamesData.get(game.getName()).incActivePlayers();
        if (game.getStatus() != eGameStatus.InProgress) {
            gamesData.get(game.getName()).changeStatus("Inactive");
        } else {
            gamesData.get(game.getName()).changeStatus("Active");
        }
    }

    public GameData getSpecificGameDataByUserName(String userName) {
        for (Game data : games.values()) {
            if (data.isGameContainsUserName(userName)) {
                return getSpecificGameData(data.getName());
            }
        }
        return null;
    }

    public Game getSpecificGameByUserName(String userName) {
        for (Game data : games.values()) {
            if (data.isGameContainsUserName(userName)) {
                return data;
            }
        }
        return null;
    }

    public void QuitGame(String gameName, String userName) {
        Game game = getSpecificGame(gameName);
        GameData gameData = getSpecificGameData(gameName);
        Player player = game.getPlayerFromName(userName);
        game.PlayerQuit(player);
        game.incVersionNum();
        gameData.decActivePlayers();
        if (game.getStatus() == eGameStatus.EndWithWin) {
            if (gameData.getActivePlayers() == 0) {
                restartGame(game);
            }
        }
    }

    private void restartGame(Game game) {
        Game newGame = null;
        games.remove(game.getName());
        if (game.getVariant() == eGameVariant.Regular) {
            newGame = new RegularGame(game);
            games.put(game.getName(), newGame);
        } else if (game.getVariant() == eGameVariant.Circular) {
            newGame = new CircularGame(game);
            games.put(game.getName(), newGame);
        } else {
            newGame = new PopoutGame(game);
            games.put(game.getName(), newGame);
        }
        String creator = gamesData.get(game.getName()).getCreator();
        gamesData.remove(game.getName());
        gamesData.put(game.getName(), new GameData (creator, newGame));
    }

}
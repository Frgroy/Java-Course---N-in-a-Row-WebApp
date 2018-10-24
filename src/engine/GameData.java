package engine;

public class GameData {
    private String name;
    private String creator;
    private int row;
    private int col;
    private int target;
    private String variant;
    private int requiredPlayers;
    private int activePlayers;
    private String status;

    public int getActivePlayers() {
        return activePlayers;
    }

    public GameData(String creator, Game game) {
        this.name = game.getName();
        this.creator = creator;
        this.row = game.getBoard().getRows();
        this.col = game.getBoard().getRows();
        this.target = game.getTarget();
        this.variant = game.getVariant().toString();
        this.status = "Inactive";
        this.requiredPlayers = game.getRequiredPlayers();
        this.activePlayers = game.getPlayerList().size();
    }

    public String getName() {
        return name;

    }

    public String getCreator() {
        return creator;
    }

    public void incActivePlayers() {
        activePlayers++;
    }

    public void decActivePlayers() {
        activePlayers--;
    }

    public void changeStatus(String str) {
        status = str;
    }
}
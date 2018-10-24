package engine;

import java.io.Serializable;

public class Move implements Serializable{
    private Player executedPlayer;
    private boolean isPopout;
    private int column;
    private int row;

    public boolean isPopout() {
        return isPopout;
    }

    public void setPopout(boolean popout) {
        isPopout = popout;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Player getExecutedPlayer() {
        return executedPlayer;
    }

    public void setExecutedPlayer(Player executedPlayer) {
        this.executedPlayer = executedPlayer;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public String toString() {
        return executedPlayer.getPlayerName() + " to: " + (column + 1);
    }

    public Move(Player executedPlayer, int row, int column, boolean isPopout) {
        this.executedPlayer = executedPlayer;
        this.column = column;
        this.row = row;
        this.isPopout = isPopout;
    }
}

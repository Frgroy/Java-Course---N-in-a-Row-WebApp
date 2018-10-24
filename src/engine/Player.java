package engine;

import utils.xsdClass.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.paint.Color;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.io.Serializable;

public class Player implements Serializable{

    private String HUMAN = "HUMAN";
    private String COMPUTER = "COMPUTER";
    private int playerColor;
    private ePlayerMode playerMode;
    private String playerID;
    private String playerName;
    private List<Disc> discList;
    private int numOfPlays;

    public Player(String userName, Boolean isComputer, int colour) {
        this.playerMode =  isComputer ? ePlayerMode.Computer : ePlayerMode.Human;
        this.playerName = userName;
        this.numOfPlays = 0;
        this.playerColor = colour;
        discList = new LinkedList();
    }

    public int getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(int playerColor) {
        this.playerColor = playerColor;
    }

    public int getNumOfPlays() {
        return numOfPlays;
    }

    public void incNumOfPlays() {
        numOfPlays++;
    }

    public ePlayerMode getPlayerMode() {
        return playerMode;
    }

    public void setPlayerMode(ePlayerMode playerMode) {
        this.playerMode = playerMode;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Player(utils.xsdClass.Player player) {
        this.playerMode =  convertFromStringToPlayerMode(player.getType());
        this.playerName = player.getName();
        this.playerID = String.valueOf(player.getId());
        this.numOfPlays = 0;
        discList = new LinkedList();
    }

    private ePlayerMode convertFromStringToPlayerMode(String playerMode) {
        ePlayerMode returnedPlayerMode;
        playerMode = playerMode.toUpperCase();
        if(playerMode.equals(HUMAN)){
            returnedPlayerMode = ePlayerMode.Human;
        }
        else // if(playerMode == COMPUTER)
        {
            returnedPlayerMode = ePlayerMode.Computer;
        }

        return returnedPlayerMode;
    }

    public List<Disc> getDiscList() {
        return discList;
    }

    public void RemoveDisc(Disc discToRemove) {
        numOfPlays++;
        discList.remove(discToRemove);
    }
}



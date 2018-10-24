package engine;

import javafx.scene.paint.Color;
import utils.xsdClass.*;
import javafx.beans.property.*;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public abstract class Game implements Serializable {
    private final String REGULAR = "REGULAR";
    private final String CIRCULAR = "CIRCULAR";
    private final String POPOUT = "POPOUT";
    private String name;
    private int requiredPlayers;
    private Board board;
    private LinkedList<Player> playerList;
    private Player currentPlayer;
    private int target;
    private eGameStatus status;
    private LinkedList<Move> movesHistory;
    private eGameVariant variant;
    private Timer timer;
    protected LinkedList<String> winners = null;
    private int version;

    public int getVersion() {
        return version;
    }

    public void incVersionNum() {
        this.version++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRequiredPlayers() {
        return requiredPlayers;
    }

    public void setRequiredPlayers(int requiredPlayers) {
        this.requiredPlayers = requiredPlayers;
    }

    public LinkedList<String> getWinners() {
        return winners;
    }

    public void setPlayerList(LinkedList<Player> playerList) {
        this.playerList = playerList;
    }

    public void setMovesHistory(LinkedList<Move> movesHistory) {
        this.movesHistory = movesHistory;
    }

    public Game(GameDescriptor g) {
        int rows = g.getGame().getBoard().getRows();
        int columns = g.getGame().getBoard().getColumns().intValue();
        board = new Board(rows, columns);
        status = eGameStatus.NotStarted;
        name = g.getDynamicPlayers().getGameTitle();
        requiredPlayers = g.getDynamicPlayers().getTotalPlayers();
        target = g.getGame().getTarget().intValue();
        variant = convertFromStringToVariant(g.getGame().getVariant());
        movesHistory = new LinkedList<>();
        playerList = new LinkedList<>();
        version = 0;
    }

    public Game (Game g) {
        board = new Board(g.getBoard().getRows(), g.getBoard().getCols());
        status = eGameStatus.NotStarted;
        name = g.getName();
        requiredPlayers = g.getRequiredPlayers();
        target = g.getTarget();
        variant = g.getVariant();
        movesHistory = new LinkedList<>();
        playerList = new LinkedList<>();
        version = 0;
    }


    private eGameVariant convertFromStringToVariant(String variant) {
        eGameVariant returnedVariant;
        variant = variant.toUpperCase();
        if (variant.equals(REGULAR)) {
            returnedVariant = eGameVariant.Regular;
        } else if (variant.equals(CIRCULAR)) {
            returnedVariant = eGameVariant.Circular;
        } else {
            returnedVariant = eGameVariant.Popout;
        }

        return returnedVariant;
    }


    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }


    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public eGameStatus getStatus() {
        return status;
    }

    public void setStatus(eGameStatus status) {
        this.status = status;
    }

    public LinkedList<Move> getMovesHistory() {
        return movesHistory;
    }

    public Timer getTimer() {
        return timer;
    }

    public eGameVariant getVariant() {
        return variant;
    }

    public void setVariant(eGameVariant variant) {
        this.variant = variant;
    }

    public void PlayAMove(int chosenColumn, boolean isPopout) {
        InsertDiscToColumn(chosenColumn);
        handleEndOfTurn();
        incVersionNum();
    }

    public void InsertDiscToColumn(int chosenColumn) {
        int row = endOfColumn(chosenColumn);
        Disc insertedDisc = new Disc(currentPlayer, new Position(row, chosenColumn));
        board.setDisc(row, chosenColumn, insertedDisc);
        currentPlayer.getDiscList().add(insertedDisc);
        currentPlayer.incNumOfPlays();
        Move executedMove = new Move(currentPlayer, row, chosenColumn, false);
        movesHistory.add(executedMove);
    }

    private int endOfColumn(int chosenColumn) {
        int i = 0;
        boolean reached = false;
        while (i < board.getRows() && !reached) {
            if (board.getDisc(i, chosenColumn) == null) {
                i++;
            } else {
                reached = true;
            }
        }
        return i - 1;
    }

    public void SwapPlayers() {
        Player firstPlayer = playerList.removeFirst();
        playerList.addLast(firstPlayer);
        currentPlayer = playerList.getFirst();
    }


    public LinkedList<String> CheckIfWinAchieved() {
        LinkedList<String> winner = new LinkedList<String>();
        Disc insertedDisc = currentPlayer.getDiscList().get(currentPlayer.getDiscList().size() - 1);
        if (checkNinaRow(insertedDisc, new Direction().new Horizontal()) ||
                checkNinaRow(insertedDisc, new Direction().new Vertical()) ||
                checkNinaRow(insertedDisc, new Direction().new FirstObliqueLineStartDownLeft()) ||
                checkNinaRow(insertedDisc, new Direction().new SecondObliqueLineStartDownRight())) {
            winner.add(insertedDisc.getPlayer().getPlayerName());
            this.status = eGameStatus.EndWithWin;
        }

        return winner;
    }

    public boolean checkNinaRow(Disc insertedDiscToCompare, Direction direction) {
        boolean weGotNinaInARow;
        int targetInARow = 1;
        targetInARow += countOccupiedSquares(insertedDiscToCompare, direction.getFirstX(), direction.getFirstY());
        weGotNinaInARow = checkIfWeGotTargetInARow(targetInARow);
        if (!weGotNinaInARow) {
            targetInARow += countOccupiedSquares(insertedDiscToCompare, direction.getSecondX(), direction.getSecondY());
            weGotNinaInARow = checkIfWeGotTargetInARow(targetInARow);
        }

        return weGotNinaInARow;
    }

    public int countOccupiedSquares(Disc insertedDiscToCompare, int addToRow, int addToColumn) {
        int tempXPosition = insertedDiscToCompare.getPosition().getRow();
        int tempYPosition = insertedDiscToCompare.getPosition().getCol();
        boolean stillCurrentPlayerDiscSign = true;
        int countedMatchSigns = 0;
        tempXPosition += addToRow;
        tempYPosition += addToColumn;
        while (isInRange(tempXPosition, tempYPosition) && stillCurrentPlayerDiscSign) {
            if (board.getDisc(tempXPosition, tempYPosition) != null) {
                if (board.getDisc(tempXPosition, tempYPosition).getPlayer() == insertedDiscToCompare.getPlayer()) {
                    countedMatchSigns++;
                    tempXPosition += addToRow;
                    tempYPosition += addToColumn;
                } else
                    stillCurrentPlayerDiscSign = false;
            } else {
                stillCurrentPlayerDiscSign = false;
            }
        }

        return countedMatchSigns;
    }

    private boolean checkIfWeGotTargetInARow(int targetInARow) {
        if (targetInARow >= target) {
            return true;
        } else
            return false;
    }

    public boolean isInRange(int xPositionCur, int yPositionCur) {
        if (xPositionCur < board.getRows() && xPositionCur >= 0 && yPositionCur >= 0 && yPositionCur < board.getCols()) {
            return true;
        }
        return false;
    }

    public boolean CheckIfBoardFulled() {
        for (int i = 0; i < board.getCols(); i++) {
            if (board.IsColumnVacant(i)) {
                return false;
            }
        }

        return true;
    }

    public Move PlayComputerTurn() {
        boolean turnPlayed = false;
        Random randomCol = new Random();
        int selectedColumn;

        do {
            selectedColumn = randomCol.nextInt(board.getCols());
            if (board.IsColumnVacant(selectedColumn)) {
                turnPlayed = true;
                PlayAMove(selectedColumn, false);
                return movesHistory.getLast();
            }
        } while (!turnPlayed);

        return null;
    }

    public void UndoMove(Move moveToRemove) {
        Disc discToRemove = board.getDisc(moveToRemove.getRow(), moveToRemove.getColumn());
        moveToRemove.getExecutedPlayer().RemoveDisc(discToRemove);
        board.RemoveDiscFromColumn(moveToRemove.getRow(), moveToRemove.getColumn());
        movesHistory.remove(moveToRemove);
        SwapPlayers();
    }

    public void StartTimer() {
        timer = new Timer();
    }

    protected void handleEndOfTurn() {
        if (!CheckIfWinAchieved().isEmpty()) {
            setStatus(eGameStatus.EndWithWin);
            winners = CheckIfWinAchieved();
        } else if (CheckIfBoardFulled()) {
            setStatus(eGameStatus.EndWithDraw);
        } else {
            SwapPlayers();
        }
    }

    public int nextAvailableRow(int column) {
        return endOfColumn(column);
    }

    public boolean ExecuteTurn(int column, boolean isPopout) {
        boolean isTurnExecuted = false;
        if (isPopout) {
            if (isColumnNotEmpty(column)) {
                if (isPopoutDiscMatchToPlayer(column)) {
                    PlayAMove(column, isPopout);
                    isTurnExecuted = true;
                }
            }
        } else {
            if (isColumnVacant(column)) {
                PlayAMove(column, isPopout);
                isTurnExecuted = true;
            }
        }

        return isTurnExecuted;
    }

    private boolean isPopoutDiscMatchToPlayer(int column) {
        return board.getLastDiscInColumn(column).getPlayer() == this.getCurrentPlayer();
    }

    private boolean isColumnNotEmpty(int column) {
        return endOfColumn(column) != board.getRows() - 1;
    }

    private boolean isColumnVacant(int column) {
        return endOfColumn(column) >= 0;
    }

    public void Start() {
        if (thereIsHuman()) {
            setCurrentPlayer(getPlayerList().get(0));
            setStatus(eGameStatus.InProgress);
            while (getCurrentPlayer().getPlayerMode() == ePlayerMode.Computer &&
                    getStatus() == eGameStatus.InProgress) {
                PlayComputerTurn();
            }
        } else {
            setStatus(eGameStatus.NoHuman);
        }
    }

    private boolean thereIsHuman() {
        for (Player p : playerList) {
            if (p.getPlayerMode() == ePlayerMode.Human) {
                return true;
            }
        }

        return false;
    }

    public void addPlayer(String userName, Boolean isComputer) {
        Player player = null;
        switch (playerList.size()) {
            case 0:
                player = new Player(userName, isComputer, 1);
                break;
            case 1:
                player = new Player(userName, isComputer, 2);
                break;
            case 2:
                player = new Player(userName, isComputer, 3);
                break;
            case 3:
                player = new Player(userName, isComputer, 4);
                break;
            case 4:
                player = new Player(userName, isComputer, 5);
                break;
            case 5:
                player = new Player(userName, isComputer, 6);
                break;
        }
        playerList.add(player);
        incVersionNum();
        if (playerList.size() == requiredPlayers) {
            this.Start();
        }
    }

    public boolean hasRoom() {
        return playerList.size() < requiredPlayers;
    }

    public boolean isGameContainsUserName(String userName) {
        for (Player p : playerList) {
            if (p.getPlayerName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public Player getPlayerFromName(String userName) {
        for (Player p : playerList) {
            if (p.getPlayerName().equals(userName)) {
                return p;
            }
        }
        return null;
    }

    public void PlayerQuit(Player player) {
        removePlayersDiscks(player);
        minimizeBoard();
        if (getCurrentPlayer() != null) {
            if (player.getPlayerName().equals(getCurrentPlayer().getPlayerName())) {
                SwapPlayers();
            }
        }
        playerList.remove(player);
        if (playerList.size() == 1) {
            if (this.status == eGameStatus.InProgress) {
                this.status = eGameStatus.EndWithWin;
                //this.winners.add(getCurrentPlayer().getPlayerName());
            }
        }
    }

    private void minimizeBoard() {
        for (int i = 0; i < board.getCols(); i++) {
            for (int j = board.getRows() - 1; j > 0; j--) {
                if (board.getDisc(j, i) == null && existingUpperDiscs(i, j)) {
                    for (int x = j; x > 0; x--) {
                        board.setDisc(x, i, board.getDisc(x - 1, i));
                    }
                    board.setDisc(0, i, null);
                    j++;
                }
            }
        }
    }

    private boolean existingUpperDiscs(int col, int row) {
        for (int i = 0; i < board.getRows(); i++) {
            if (board.getDisc(i, col) != null) {
                return i < row;
            }
        }
        return false;
    }

    private void removePlayersDiscks(Player player) {
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                if (board.getDisc(i, j) != null) {
                    if (board.getDisc(i, j).getPlayer() == player) {
                        board.RemoveDiscFromColumn(i, j);
                    }
                }
            }
        }
    }
}


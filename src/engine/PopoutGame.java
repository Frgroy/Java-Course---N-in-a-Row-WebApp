package engine;
import utils.xsdClass.*;
import java.io.Serializable;
import java.util.LinkedList;

public class PopoutGame extends Game implements Serializable {
    public PopoutGame(GameDescriptor g) {
        super(g);
    }

    public PopoutGame(Game g) { super(g);}

    @Override
    public void PlayAMove(int chosenColumn, boolean isPopout)
    {
        if (!isPopout){
            InsertDiscToColumn(chosenColumn);
        }
        else {
            PopDiscOutOfColumn(chosenColumn);
        }

        handleEndOfTurn();
        incVersionNum();
    }

    private void PopDiscOutOfColumn(int chosenColumn) {
        super.getMovesHistory().add(new Move (super.getCurrentPlayer(), getBoard().getRows()-1, chosenColumn, true));
        Disc discToRemove = super.getBoard().getDisc(getBoard().getRows()-1, chosenColumn);
        super.getCurrentPlayer().RemoveDisc(discToRemove);
        super.getBoard().RemoveDiscFromColumn(getBoard().getRows()-1, chosenColumn);
        super.getBoard().ScrollDownDiscs(chosenColumn);
    }

    @Override
    public LinkedList<String> CheckIfWinAchieved() {
        LinkedList<String> winners = new LinkedList();
        Move actionedMove = super.getMovesHistory().getLast();
        int i = super.getBoard().getRows() - 1;
        Disc checkedDisc = super.getBoard().getDisc(i, actionedMove.getColumn());
        while (checkedDisc != null) {
            if (checkNinaRow(checkedDisc, new Direction().new Horizontal()) ||
                    checkNinaRow(checkedDisc, new Direction().new Vertical()) ||
                    checkNinaRow(checkedDisc, new Direction().new FirstObliqueLineStartDownLeft()) ||
                    checkNinaRow(checkedDisc, new Direction().new SecondObliqueLineStartDownRight())) {
                super.setStatus(eGameStatus.EndWithWin);
                if (!winners.contains(checkedDisc.getPlayer().getPlayerName())) {
                    winners.add(checkedDisc.getPlayer().getPlayerName());
                }
            }
            i--;
            if (i >= 0) {
                checkedDisc = super.getBoard().getDisc(i, actionedMove.getColumn());
            } else {
                checkedDisc = null;
            }
        }
        return winners;
    }

    protected void handleEndOfTurn() {
        if (!CheckIfWinAchieved().isEmpty()){
            setStatus(eGameStatus.EndWithWin);
            super.winners = CheckIfWinAchieved();
        } else if (CheckIfBoardFulled()){
            setStatus(eGameStatus.EndWithDraw);
        } else{
            SwapPlayers();
        }
    }
}

package engine;



import java.io.Serializable;
import utils.xsdClass.*;
public class CircularGame extends Game implements Serializable {

    public CircularGame(utils.xsdClass.GameDescriptor g) {
        super(g);
    }

    public CircularGame (Game g) {super(g);}

    @Override
    public int countOccupiedSquares(Disc insertedDiscToCompare, int addToRow, int addToColumn )
    {
        int tempXPosition = insertedDiscToCompare.getPosition().getRow();
        int tempYPosition = insertedDiscToCompare.getPosition().getCol();
        boolean stillCurrentPlayerDiscSign = true;
        int countedMatchSigns = 0;
        tempXPosition += addToRow;
        tempYPosition += addToColumn;
        if (!isInRange(tempXPosition, tempYPosition)){
            if (addToRow == 0 || addToColumn == 0) {
                tempXPosition = changeCircularXPosition(tempXPosition);
                tempYPosition = changeCircularYPosition(tempYPosition);
            }
        }
        while (isInRange(tempXPosition, tempYPosition) && stillCurrentPlayerDiscSign) {
            if (super.getBoard().getDisc(tempXPosition, tempYPosition) != null)
            {
                if (super.getBoard().getDisc(tempXPosition,tempYPosition).getPlayer() == insertedDiscToCompare.getPlayer()){
                    countedMatchSigns++;
                    tempXPosition += addToRow;
                    tempYPosition += addToColumn;
                    if (!isInRange(tempXPosition, tempYPosition)){
                        if (addToRow == 0 || addToColumn == 0) {
                            tempXPosition = changeCircularXPosition(tempXPosition);
                            tempYPosition = changeCircularYPosition(tempYPosition);
                        }
                    }
                }
                else
                    stillCurrentPlayerDiscSign = false;
            }
            else{
                stillCurrentPlayerDiscSign = false;
            }
        }
        return countedMatchSigns;
    }

    private int changeCircularXPosition(int xPosition) {
        int returnedXPosition;
        if (xPosition == super.getBoard().getRows()) {
            returnedXPosition = 0;
        } else if (xPosition == -1 ) {
            returnedXPosition = super.getBoard().getRows() - 1;
        } else {
            returnedXPosition = xPosition;
        }

        return returnedXPosition;
    }

    private int changeCircularYPosition(int yPosition) {
        int returnedYPosition;
        if (yPosition == super.getBoard().getCols()) {
            returnedYPosition = 0;
        } else if (yPosition == -1 ) {
            returnedYPosition = super.getBoard().getCols() - 1;
        } else {
            returnedYPosition = yPosition;
        }

        return returnedYPosition;
    }
}


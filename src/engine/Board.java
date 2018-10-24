package engine;


import java.io.Serializable;

public class Board implements Serializable{
    private Square[][] board;
    private int rows;
    private int columns;

    public Board(int i_Rows, int i_Columns) {
        board = new Square[i_Rows][i_Columns];
        rows = i_Rows;
        columns = i_Columns;

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                board[i][j] = new Square();
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return columns;
    }

    public void setCols(int cols) {
        this.columns = cols;
    }

    public Disc getDisc(int row, int column)
    {
        return board[row][column].getCurrentDisc();
    }

    public void setDisc(int row, int column, Disc disc)
    {
        board[row][column].setCurrentDisc(disc);
    }

    public boolean IsColumnVacant(int chosenColumn) {
        return board[0][chosenColumn].getCurrentDisc() == null;
    }

    public void RemoveDiscFromColumn(int row, int column) {
        board[row][column].getCurrentDisc().getPlayer().getDiscList().remove(board[row][column].getCurrentDisc());
        board[row][column].setCurrentDisc(null);
    }

    public void ScrollDownDiscs(int chosenColumn) {
        int i = 2;
        Disc discToScroll = getDisc(getRows()-2, chosenColumn);
        while (discToScroll != null){
            discToScroll.ScrollDown();
            RemoveDiscFromColumn(getRows()- i, chosenColumn);
            setDisc(getRows()- i + 1, chosenColumn, discToScroll);
            discToScroll = getDisc(getRows()-(++i), chosenColumn);
        }
    }

    public Disc getLastDiscInColumn(int column) {
        return board[rows-1][column].getCurrentDisc();
    }
}

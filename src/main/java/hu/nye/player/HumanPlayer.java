package hu.nye.player;

import hu.nye.board.Board;

/**
 * The HumanPlayer class represents a human player in the game.
 */
public class HumanPlayer extends Player {
    /**
     * Constructs a new HumanPlayer with the specified symbol and board.
     *
     * @param symbol the symbol representing the human player (e.g., 'Y' for Yellow)
     * @param board the Board object representing the game board
     */
    public HumanPlayer(char symbol, Board board) {
        super(symbol, board);
    }

    public void makeMove(int column) {
        makeMove(column, false);
    }

    /**
     * Makes a move by placing a disc in the specified column if it is valid and not full.
     *
     * @param column the column to place the disc in
     */
    @Override
    public void makeMove(int column, boolean isTestOfWinning) {
        if (column >= 0 && column < board.getColumns() && !board.isColumnFull(column)) {
            board.placeDisc(symbol, column);
        } else {
            System.out.println("Érvénytelen oszlop vagy a kiválasztott oszlop már tele van!");
        }
    }
}
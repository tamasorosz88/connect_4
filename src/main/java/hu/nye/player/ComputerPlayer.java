package hu.nye.player;

import hu.nye.board.Board;
import java.util.Random;

/**
 * The ComputerPlayer class represents a computer-controlled player in the game.
 */
public class ComputerPlayer extends Player {
    private final Random random;

    /**
     * Constructs a new ComputerPlayer with the specified symbol and board.
     *
     * @param symbol the symbol representing the computer player (e.g., 'R' for Red)
     * @param board the Board object representing the game board
     */
    public ComputerPlayer(char symbol, Board board) {
        super(symbol, board);
        this.random = new Random();
    }

    public void makeMove(int column) {
        makeMove(column, false);
    }

    /**
     * Makes a move by placing a disc in a random column that is not full.
     *
     * @param column the column to place the disc in (not used for computer player)
     */
    @Override
    public void makeMove(int column, boolean isTestOfWinning) {
        int col;
        do {
            col = random.nextInt(board.getColumns());
        } while (board.isColumnFull(col));
        if(!isTestOfWinning) {
            board.placeDisc(symbol, col);
        } else {
            board.placeDisc(symbol, 0);
        }
    }
}
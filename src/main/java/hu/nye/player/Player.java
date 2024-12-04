package hu.nye.player;

import hu.nye.board.Board;

/**
 * The Player class is an abstract class that represents a player in the game.
 */
public abstract class Player {
    protected char symbol;   // The symbol of the player ('Y' or 'R')
    protected final Board board;

    /**
     * Constructs a new Player with the specified symbol and board.
     *
     * @param symbol the symbol representing the player
     * @param board the Board object representing the game board
     */
    public Player(char symbol, Board board) {
        this.symbol = symbol;
        this.board = board;
    }

    /**
     * Returns the symbol of the player.
     *
     * @return the symbol of the player
     */
    public char getSymbol() {
        return symbol;
    }

    public abstract void makeMove(int column);

    /**
     * Makes a move by placing a disc in the specified column.
     *
     * @param column the column to place the disc in
     */
    public abstract void makeMove(int column, boolean isTestOfWinning);
}
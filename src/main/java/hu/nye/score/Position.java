package hu.nye.score;

/**
 * The Position class represents a position on the game board, identified by its
 * row and column.
 */
public class Position {
    private final int row;
    private final int column;

    /**
     * Constructs a new Position with the specified row and column.
     *
     * @param row    the row of the position
     * @param column the column of the position
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Returns the row of the position.
     *
     * @return the row of the position
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column of the position.
     *
     * @return the column of the position
     */
    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return row + "," + column;
    }
}
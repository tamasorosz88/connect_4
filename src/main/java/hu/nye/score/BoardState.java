package hu.nye.score;

import java.util.List;

/**
 * The BoardState class represents the state of the game board.
 */
public class BoardState {
    private final List<List<Disc>> board;

    /**
     * Constructs a new BoardState with the specified board configuration.
     *
     * @param board a 2D list representing the game board, where each element is a
     *              Disc
     */
    public BoardState(List<List<Disc>> board) {
        this.board = board;
    }

    /**
     * Returns the current state of the game board.
     *
     * @return a 2D list representing the game board
     */
    public List<List<Disc>> getBoard() {
        return board;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (List<Disc> row : board) {
            sb.append("[");
            for (int i = 0; i < row.size(); i++) {
                sb.append(row.get(i).getColor());
                if (i < row.size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]\n");
        }
        return sb.toString();
    }
}
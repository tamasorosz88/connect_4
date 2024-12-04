package hu.nye.player;

import hu.nye.board.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link ComputerPlayer} class.
 */
public class ComputerPlayerTest {
    private Board board;
    private ComputerPlayer computerPlayer;

    /**
     * Sets up a new Board and ComputerPlayer instance before each test.
     */
    @BeforeEach
    public void setUp() {
        board = new Board(6, 7);
        computerPlayer = new ComputerPlayer('R', board);
    }

    /**
     * Tests the {@link ComputerPlayer#makeMove(int)} method.
     * Ensures that the computer player places a disc in a valid column.
     */
    @Test
    public void testMakeMove() {
        computerPlayer.makeMove(-1);
        boolean discPlaced = false;
        for (int i = 0; i < 7; i++) {
            if (board.getBoardState()[5][i] == 'R') {
                discPlaced = true;
                break;
            }
        }
        assertTrue(discPlaced);
    }
}
package hu.nye.player;

import hu.nye.board.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Player} class.
 */
public class PlayerTest {

    private Player humanPlayer;
    private Player computerPlayer;
    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board(6, 7); // Példa tábla méret
        humanPlayer = new HumanPlayer('Y', board); // Yellow (Y)
        computerPlayer = new ComputerPlayer('R', board); // Red (R)
    }

    /**
     * Tests the {@link Player#makeMove(int)} method for the human player.
     * Ensures that the method correctly handles the player's move.
     */
    @Test
    public void testHumanPlayerMakeMove() {
        humanPlayer.makeMove(0); // Valid move
        assertEquals('Y', board.getBoardState()[5][0]); // Check if the disc is placed correctly

        // Capture the printed output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        humanPlayer.makeMove(7); // Invalid move

        // Verify the output contains expected prompts and messages
        String output = outContent.toString();
        assertTrue(output.contains("Érvénytelen oszlop vagy a kiválasztott oszlop már tele van!"));
    }

    /**
     * Tests the {@link Player#makeMove(int)} method for the computer player.
     * Ensures that the method correctly handles the player's move.
     */
    @Test
    public void testComputerPlayerMakeMove() {
        computerPlayer.makeMove(0, true); // Valid move
        assertEquals('R', board.getBoardState()[5][0]); // Check if the disc is placed correctly
    }
}
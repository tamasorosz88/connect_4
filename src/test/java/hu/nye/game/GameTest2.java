package hu.nye.game;

import hu.nye.board.Board;
import hu.nye.player.ComputerPlayer;
import hu.nye.player.HumanPlayer;
import hu.nye.player.Player;
import hu.nye.score.ScoreManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Game} class.
 */
public class GameTest2 {

    private Game game;
    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board(6, 7); // Példa tábla méret
        game = new Game(6, 7); // Példa tábla méret
    }

    /**
     * Tests the {@link Game#Game(int, int)} constructor.
     * Ensures that the constructor correctly initializes the game with the given
     * rows and columns.
     */
    @Test
    public void testGameConstructorWithRowsAndColumns() {
        assertNotNull(game);
        assertEquals(6, game.getBoard().getRows());
        assertEquals(7, game.getBoard().getColumns());
    }

    /**
     * Tests the {@link Game#getHumanPlayer()} method.
     * Ensures that the method correctly returns the human player.
     */
    @Test
    public void testGetHumanPlayer() {
        Player humanPlayer = game.getHumanPlayer();
        assertNotNull(humanPlayer);
        assertTrue(humanPlayer instanceof HumanPlayer);
        assertEquals('Y', humanPlayer.getSymbol()); // Feltételezve, hogy a humán játékos szimbóluma 'Y'
    }

    /**
     * Tests the {@link Game#getComputerPlayer()} method.
     * Ensures that the method correctly returns the computer player.
     */
    @Test
    public void testGetComputerPlayer() {
        Player computerPlayer = game.getComputerPlayer();
        assertNotNull(computerPlayer);
        assertTrue(computerPlayer instanceof ComputerPlayer);
        assertEquals('R', computerPlayer.getSymbol()); // Feltételezve, hogy a számítógép játékos szimbóluma 'R'
    }

    /**
     * Tests the human player's move and win condition.
     */
    @Test
    public void testHumanPlayerMoveAndWin() {
        // Simulate user input for player name and game actions
        String input = "Player1\na\na\na\na\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Capture the printed output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Start the game
        game.start(new Scanner(System.in), true, false);

        // Verify the output contains expected prompts and messages
        String output = outContent.toString();
        assertTrue(output.contains("Gratulálok, nyertél!"));
    }

    /**
     * Tests the computer player's move and win condition.
     */
    @Test
    public void testComputerPlayerMoveAndWin() {
        // Simulate user input for player name and game actions
        String input = "Player2\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Capture the printed output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Start the game
        game.start(new Scanner(System.in), false, true);

        // Verify the output contains expected prompts and messages
        String output = outContent.toString();
        assertTrue(output.contains("A gép nyert!"));
    }
}
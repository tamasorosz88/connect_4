package hu.nye.game;

import hu.nye.board.Board;
import hu.nye.player.ComputerPlayer;
import hu.nye.player.HumanPlayer;
import hu.nye.player.Player;

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
public class GameTest {
    private Game game;
    private Board board;

    /**
     * Sets up a new Game instance before each test.
     */
    @BeforeEach
    public void setUp() {
        board = new Board(6, 7);
        game = new Game(board);
    }

    /**
     * Tests the initialization of the Game object.
     * Ensures that the Game object is not null after initialization.
     */
    @Test
    public void testGameInitialization() {
        assertNotNull(game);
    }

    /**
     * Tests the {@link Game#start(Scanner)} method.
     * Ensures that the method correctly starts the game.
     */
    @Test
    public void testStart() {
        // Simulate user input for player name and game actions
        String input = "Player1\nCtrl + Q\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Capture the printed output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Start the game
        game.start(new Scanner(System.in));

        // Verify the output contains expected prompts and messages
        String output = outContent.toString();
        assertTrue(output.contains("Add meg a neved:"));
        assertTrue(output.contains("Humán játékos következik:"));
        assertTrue(output.contains("Kilépés a játékból..."));

        input = "Player1\nCtrl + S\n\nem\nCtrl + Q\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Capture the printed output
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Start the game
        game.start(new Scanner(System.in));

        // Verify the output contains expected prompts and messages
        output = outContent.toString();
        assertTrue(output.contains("Add meg a neved:"));
        assertTrue(output.contains("Humán játékos következik:"));
        assertTrue(output.contains("Szeretnéd menteni a játék állapotát? (igen/nem): "));
        assertTrue(output.contains("Kilépés a játékból..."));
    }

    /**
     * Tests the {@link Game#getColumnFromInput(Scanner)} method.
     * Ensures that the method correctly retrieves the column from user input.
     */
    @Test
    public void testGetColumnFromInput() {
        // Simulate user input for column selection
        String input = "a\nCtrl + S\nCtrl + Q\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        // Test valid column input
        int column = game.getColumnFromInput(scanner);
        assertEquals(0, column); // Column 1 should be converted to 0-based index

        // Test save game input
        column = game.getColumnFromInput(scanner);
        assertEquals(-1, column);

        // Test exit game input
        column = game.getColumnFromInput(scanner);
        assertEquals(-2, column);
    }

    /**
     * Tests the {@link Game#Game(Board)} constructor.
     * Ensures that the constructor correctly initializes the game with the given
     * board.
     */
    @Test
    public void testGameConstructor() {
        Board board = new Board(6, 7);
        Game game = new Game(board);
        assertNotNull(game);
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
}
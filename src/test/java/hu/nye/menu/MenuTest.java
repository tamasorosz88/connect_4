package hu.nye.menu;

import hu.nye.score.ScoreManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link Menu} class.
 */
public class MenuTest {

    private Menu menu;
    private ScoreManager mockScoreManager;

    @BeforeEach
    public void setUp() {
        mockScoreManager = mock(ScoreManager.class);
        menu = new Menu(mockScoreManager);
    }

    /**
     * Tests the {@link Menu#startNewGame(Scanner)} method.
     * Ensures that the method correctly starts a new game.
     */
    @Test
    public void testStartNewGame() {
        // Simulate user input for starting a new game
        String input = "3\n4\n13\n12\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Capture the printed output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Start a new game
        menu.startNewGame(new Scanner(System.in), true);

        // Verify the output contains expected prompts and messages
        String output = outContent.toString();
        assertTrue(output.contains("Add meg a sorok számát (4-12): ") && output.contains("Érvénytelen sor szám. Próbáld újra!") &&
                output.contains("Add meg az oszlopok számát (4-12): ") && output.contains("Érvénytelen oszlop szám. Próbáld újra!"));
    }

    /**
     * Tests the {@link Menu#displayMenu()} method.
     * Ensures that the method correctly displays the menu.
     */
    @Test
    public void testDisplayMenu() {
        // Capture the printed output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Display the menu
        menu.displayMenu(true);

        // Verify the output contains expected menu options
        String output = outContent.toString();
        assertTrue(output.contains("1. Új játék"));
        assertTrue(output.contains("2. Játék betöltése"));
        assertTrue(output.contains("3. High score megtekintése"));
        assertTrue(output.contains("4. High score törlése"));
        assertTrue(output.contains("5. Kilépés"));
        assertTrue(output.contains("Válassz egy opciót: "));
    }

    /**
     * Tests the {@link Menu#loadGame(Scanner)} method.
     * Ensures that the method correctly loads a game.
     */
    @Test
    public void testLoadGame() {
        // Simulate user input for loading a game
        String input = "test_game_state.xml\ntomi\nCtrl + Q\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Capture the printed output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Load a game
        menu.loadGame(new Scanner(System.in));

        // Verify the output contains expected prompts and messages
        String output = outContent.toString();
        assertTrue(output.contains("Kilépés a játékból..."));
    }

    /**
     * Tests the {@link Menu#Menu(ScoreManager)} constructor.
     * Ensures that the constructor correctly initializes the menu with the given
     * score manager.
     */
    @Test
    public void testMenuConstructor() {
        ScoreManager scoreManager = mock(ScoreManager.class);
        Menu menu = new Menu(scoreManager);
        assertNotNull(menu);
    }

    /**
     * Tests the {@link Menu#deletePlayerScore(Scanner)} method.
     * Ensures that the method correctly deletes a player's score.
     */
    @Test
    public void testDeletePlayerScore() {
        // Simulate user input for deleting a player's score
        String input = "Player1\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Capture the printed output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Delete a player's score
        menu.deletePlayerScore(new Scanner(System.in));

        // Verify the output contains expected prompts and messages
        String output = outContent.toString();
        assertTrue(output.contains("Add meg a játékos nevét, akinek a high score-ját törölni szeretnéd: "));
        verify(mockScoreManager, times(1)).deletePlayerScore("Player1");
    }
}
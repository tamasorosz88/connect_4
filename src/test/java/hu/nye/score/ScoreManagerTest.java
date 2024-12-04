package hu.nye.score;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link ScoreManager} class.
 */
public class ScoreManagerTest {

    private static final String DB_URL = "jdbc:sqlite:test_connect4.db";
    private ScoreManager scoreManager;
    private Connection connection;

    @BeforeEach
    public void setUp() throws Exception {
        // Create a connection to the SQLite database
        connection = DriverManager.getConnection(DB_URL);

        // Create the HighScores table
        try (Statement stmt = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS HighScores (" +
                    "name TEXT PRIMARY KEY, " +
                    "wins INTEGER)";
            stmt.execute(sql);
        }

        // Insert initial data with a check for existence
        try (PreparedStatement pstmt = connection
                .prepareStatement("SELECT COUNT(*) FROM HighScores WHERE name = ?")) {
            pstmt.setString(1, "Player1");
            ResultSet rs = pstmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                // If Player1 doesn't exist, insert it
                try (PreparedStatement insertStmt = connection
                        .prepareStatement("INSERT INTO HighScores (name, wins) VALUES (?, ?)")) {
                    insertStmt.setString(1, "Player1");
                    insertStmt.setInt(2, 5);
                    insertStmt.executeUpdate();
                }
            }

            // Same for Player2
            pstmt.setString(1, "Player2");
            rs = pstmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                try (PreparedStatement insertStmt = connection
                        .prepareStatement("INSERT INTO HighScores (name, wins) VALUES (?, ?)")) {
                    insertStmt.setString(1, "Player2");
                    insertStmt.setInt(2, 2);
                    insertStmt.executeUpdate();
                }
            }
        }

        // Create a new ScoreManager instance
        scoreManager = new ScoreManager();
    }

    @AfterEach
    public void tearDown() throws Exception {
        // Close the connection
        if (connection != null) {
            connection.close();
        }
    }

    /**
     * Tests the {@link ScoreManager#saveWin(String)} method.
     * Ensures that the method correctly saves a win for a player.
     */
    @Test
    public void testSaveWin() throws Exception {
        scoreManager.saveWin("Player1", DB_URL);

        // Verify the win count for Player1
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT wins FROM HighScores WHERE name = ?")) {
            pstmt.setString(1, "Player1");
            try (ResultSet rs = pstmt.executeQuery()) {
                assertTrue(rs.next());
                assertEquals(6, rs.getInt("wins")); // Player1 should have 6 wins now
            }
        }
    }

    /**
     * Tests the {@link ScoreManager#deletePlayerScore(String)} method.
     * Ensures that the method correctly deletes a specific player's high score.
     */
    @Test
    public void testDeletePlayerScore() throws Exception {
        // Capture the printed output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Törlés végrehajtása
        scoreManager.deletePlayerScore("Player1", DB_URL);

        // Ellenőrizze, hogy a megfelelő üzenet megjelent
        String output = outContent.toString();
        assertTrue(output.contains("High score törölve: "));

        // Ellenőrizze, hogy a Player1 rekord tényleg eltávolításra került
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM HighScores WHERE name = ?")) {
            pstmt.setString(1, "Player1");
            ResultSet rs = pstmt.executeQuery();

            // Ha a Player1 nem létezik, akkor a COUNT értéke 0 legyen
            if (rs.next()) {
                int count = rs.getInt(1);
                assertEquals(0, count); // Ellenőrizzük, hogy 0 találat van (törölték)
            }
        }

        // Ellenőrizze, hogy a Player2 rekord nem lett érintve
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM HighScores WHERE name = ?")) {
            pstmt.setString(1, "Player2");
            ResultSet rs = pstmt.executeQuery();

            // Ha a Player2 létezik, akkor a COUNT értéke 1 legyen
            if (rs.next()) {
                int count = rs.getInt(1);
                assertEquals(1, count); // Ellenőrizzük, hogy 1 találat van (nem törölték)
            }
        }

        // Negatív teszt: próbálja törölni egy nem létező játékos rekordját (Player3)
        // Capture the printed output
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Törlés végrehajtása
        scoreManager.deletePlayerScore("Player3", DB_URL);

        // Ellenőrizze, hogy a megfelelő üzenet megjelent
        output = outContent.toString();
        assertTrue(output.contains("Nincs ilyen nevű játékos: "));
    }
}
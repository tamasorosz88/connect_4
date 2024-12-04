package hu.nye.score;

import java.sql.*;

/**
 * The ScoreManager class manages the high scores for the game, including saving
 * wins and retrieving high scores.
 */
public class ScoreManager {
    private static final String DB_URL = "jdbc:sqlite:connect4.db";

    /**
     * Constructs a new ScoreManager and creates the high scores table if it does
     * not exist.
     */

    public ScoreManager() {
        try {
            // Explicitly load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver betöltési hiba: " + e.getMessage());
        }
        createTableIfNotExists();
    }

    /**
     * Creates the high scores table if it does not exist.
     */
    private void createTableIfNotExists() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS HighScores (" +
                    "name TEXT PRIMARY KEY, " +
                    "wins INTEGER)";
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Adatbázis hiba: " + e.getMessage());
        }
    }

    public void saveWin(String playerName) {
        saveWin(playerName, DB_URL);
    }

    /**
     * Saves a win for the specified player. If the player already exists, their win
     * count is incremented.
     *
     * @param playerName the name of the player
     */
    public void saveWin(String playerName, String DB_URL) {
        String query = "INSERT INTO HighScores(name, wins) VALUES(?, 1) ON CONFLICT(name) " +
                "DO UPDATE SET wins = wins + 1";
        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, playerName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Hiba mentés közben: " + e.getMessage());
        }
    }

    /**
     * Retrieves and prints the high scores, ordered by the number of wins in
     * descending order.
     */
    public void getHighScores() {
        String query = "SELECT name, wins FROM HighScores ORDER BY wins DESC";
        try (Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("+-----------------+----------+");
            System.out.println("| Játékos neve    | Győzelmek|");
            System.out.println("+-----------------+----------+");
            while (rs.next()) {
                String name = rs.getString("name");
                int wins = rs.getInt("wins");
                System.out.printf("| %-15s | %7d |\n", name, wins);
            }
            System.out.println("+-----------------+----------+");
        } catch (SQLException e) {
            System.out.println("Hiba lekérés közben: " + e.getMessage());
        }
    }

    public void deletePlayerScore(String playerName) {
        deletePlayerScore(playerName, DB_URL);
    }

    /**
     * Deletes the high score of a specific player from the database.
     *
     * @param playerName the name of the player whose high score should be deleted
     */
    public void deletePlayerScore(String playerName, String DB_URL) {
        String query = "DELETE FROM HighScores WHERE name = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, playerName);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("High score törölve: " + playerName);
            } else {
                System.out.println("Nincs ilyen nevű játékos: " + playerName);
            }
        } catch (SQLException e) {
            System.out.println("Hiba törlés közben: " + e.getMessage());
        }
    }
}
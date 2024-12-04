package hu.nye.menu;

import hu.nye.board.Board;
import hu.nye.util.GameSaver;
import hu.nye.game.Game;
import hu.nye.score.ScoreManager;

import java.util.Scanner;

/**
 * The Menu class provides a user interface for starting a new game or loading an existing game.
 */
public class Menu {

    private Scanner scanner = new Scanner(System.in);
    private ScoreManager scoreManager;

    public Menu(ScoreManager scoreManager) {
        this.scoreManager = scoreManager;
    }

    public void displayMenu() {
        displayMenu(false);
    }

    /**
     * Displays the main menu and handles user input for menu options.
     */
    public void displayMenu(boolean isTestOfDisplayMenu) {
        System.out.println("1. Új játék");
        System.out.println("2. Játék betöltése");
        System.out.println("3. High score megtekintése");
        System.out.println("4. High score törlése");
        System.out.println("5. Kilépés");
        System.out.print("Válassz egy opciót: ");
        if (!isTestOfDisplayMenu) {
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    startNewGame(scanner);
                    break;
                case 2:
                    loadGame(scanner);
                    break;
                case 3:
                    scoreManager.getHighScores();
                    break;
                case 4:
                    deletePlayerScore(scanner);
                    break;
                case 5:
                    System.out.println("Kilépés...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Érvénytelen választás.");
                    displayMenu();
                    break;
            }
        }
    }

    public void startNewGame(Scanner scanner) {
        startNewGame(scanner, false);
    }

    /**
     * Starts a new game by prompting the user for the board size and initializing the game.
     */
    public void startNewGame(Scanner scanner, boolean isTestOfMenu) {
        int rows = 0;
        int columns = 0;

        // Prompt the user for the board size
        while (rows < 4 || rows > 12) {
            System.out.print("Add meg a sorok számát (4-12): ");
            rows = scanner.nextInt();
            if (rows < 4 || rows > 12) {
                System.out.println("Érvénytelen sor szám. Próbáld újra!");
            }
        }

        while (columns < 4 || columns > 12) {
            System.out.print("Add meg az oszlopok számát (4-12): ");
            columns = scanner.nextInt();
            if (columns < 4 || columns > 12) {
                System.out.println("Érvénytelen oszlop szám. Próbáld újra!");
            }
        }

        if(!isTestOfMenu) {
            // Create the game with the specified board size
            Game game = new Game(rows, columns);
            // This was the old parameter "new Scanner(System.in)"
            game.start(new Scanner(System.in));
        }
    }

    /**
     * Loads an existing game from a file specified by the user.
     */
    public void loadGame(Scanner scanner) {
        System.out.print("Add meg a fájl nevét: ");
        String fileName = scanner.nextLine();
        Board board = GameSaver.loadBoardFromFile(fileName);
        if (board != null) {
            Game game = new Game(board);
            game.start(scanner);
        } else {
            System.out.println("Nem sikerült betölteni a játékot.");
            displayMenu();
        }
    }

    public void deletePlayerScore(Scanner scanner) {
        System.out.print("Add meg a játékos nevét, akinek a high score-ját törölni szeretnéd: ");
        String playerName = scanner.nextLine();
        scoreManager.deletePlayerScore(playerName);
    }
}
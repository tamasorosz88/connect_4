package hu.nye.game;

import hu.nye.board.Board;
import hu.nye.util.GameSaver;
import hu.nye.player.ComputerPlayer;
import hu.nye.player.HumanPlayer;
import hu.nye.player.Player;
import hu.nye.score.ScoreManager;
import hu.nye.menu.Menu;

import java.util.Scanner;

/**
 * The Game class represents the main game logic for a Connect Four game.
 */
public class Game {
    private final Board board;
    private final Player humanPlayer;
    private final Player computerPlayer;
    private final ScoreManager scoreManager;
    private String playerName;

    /**
     * Constructs a new Game with the specified number of rows and columns.
     *
     * @param rows the number of rows for the game board
     * @param columns the number of columns for the game board
     */
    public Game(int rows, int columns) {
        this.board = new Board(rows, columns);
        this.humanPlayer = new HumanPlayer('Y', board);  // Yellow (Y)
        this.computerPlayer = new ComputerPlayer('R', board);  // Red (R)
        this.scoreManager = new ScoreManager();  // Initialization
    }

    /**
     * Constructs a new Game with the specified board.
     *
     * @param board the Board object representing the current state of the game
     */
    public Game(Board board) {
        this.board = board;
        this.humanPlayer = new HumanPlayer('Y', board);  // Yellow (Y)
        this.computerPlayer = new ComputerPlayer('R', board);  // Red (R)
        this.scoreManager = new ScoreManager();  // Initialization
    }

    public void start(Scanner scanner) {
        start(scanner, false, false);
    }

    /**
     * Starts the game and handles the main game loop.
     */
    public void start(Scanner scanner, boolean isTestOfWinningOfHumanPlayer, boolean isTestOfWinningOfComputerPlayer) {
        System.out.print("Add meg a neved: ");
        playerName = scanner.nextLine();

        while (true) {
            board.printBoard();

            // Human player's turn
            if(!isTestOfWinningOfComputerPlayer) {
                System.out.println("\033[1;33mHumán játékos következik: " + humanPlayer.getSymbol() + "\033[0m"); // Yellow
                int column = getColumnFromInput(scanner);  // Read the column
                if (column == -1) {
                    // Save the game
                    System.out.print("Szeretnéd menteni a játék állapotát? (igen/nem): ");
                    String saveChoice = scanner.nextLine();
                    if (saveChoice.equalsIgnoreCase("igen")) {
                        GameSaver.saveBoardToFile(board, "game_state.xml");
                    }
                    continue; // Skip the rest of the loop and prompt for the next move
                } else if (column == -2) {
                    // Exit the game
                    System.out.println("Kilépés a játékból...");
                    break;
                }
                humanPlayer.makeMove(column);  // Pass the selected column
                if (board.checkWin(humanPlayer.getSymbol())) {
                    board.printBoard();
                    System.out.println("\033[1;33mGratulálok, nyertél!\033[0m"); // Yellow
                    scoreManager.saveWin(playerName);
                    break;
                }
                if (board.isFull()) {
                    board.printBoard();
                    System.out.println("\033[1;33mDöntetlen!\033[0m"); // Yellow
                    break;
                }
            }

            // Computer player's turn
            if(!isTestOfWinningOfHumanPlayer) {
                System.out.println("\033[1;31mA gép lépett.\033[0m");// Red
                if(isTestOfWinningOfComputerPlayer) {
                    computerPlayer.makeMove(-1, true);// No need to specify a column
                } else {
                    computerPlayer.makeMove(-1);// No need to specify a column
                }
                if (board.checkWin(computerPlayer.getSymbol())) {
                    board.printBoard();
                    System.out.println("\033[1;31mA gép nyert!\033[0m"); // Red
                    break;
                }
            }
        }

        scoreManager.getHighScores();
        scanner.close();
    }

    /**
     * Prompts the user to enter a column and handles special commands for saving or exiting the game.
     *
     * @param scanner the Scanner object to read user input
     * @return the selected column, or a special value for save (-1) or exit (-2) commands
     */
    public int getColumnFromInput(Scanner scanner) {
        String input;
        while (true) {
            System.out.print("Add meg az oszlopot (a-g), írja be a 'Ctrl + S' kombinációt a mentéshez, vagy a 'Ctrl + Q' kombinációt a kilépéshez: ");
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("Ctrl + S")) {
                return -1; // Special value to indicate save command
            }
            if (input.equalsIgnoreCase("Ctrl + Q")) {
                return -2; // Special value to indicate exit command
            }
            if (input.length() == 1 && input.charAt(0) >= 'a' && input.charAt(0) <= 'g') {
                return input.charAt(0) - 'a'; // 'a' -> 0, 'b' -> 1, ..., 'g' -> 6
            } else {
                System.out.println("Érvénytelen oszlop. Kérlek, válassz a-b-c-d-e-f-g között.");
            }
        }
    }

    /**
     * Returns the human player.
     *
     * @return the HumanPlayer object representing the human player
     */
    public HumanPlayer getHumanPlayer() {
        return (HumanPlayer) humanPlayer;
    }

    /**
     * Returns the game board.
     *
     * @return the Board object representing the game board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Returns the computer player.
     *
     * @return the ComputerPlayer object representing the computer player
     */
    public ComputerPlayer getComputerPlayer() {
        return (ComputerPlayer) computerPlayer;
    }

    /**
     * The main method to start the game.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        ScoreManager scoreManager = new ScoreManager();
        Menu menu = new Menu(scoreManager);
        menu.displayMenu();
    }
}
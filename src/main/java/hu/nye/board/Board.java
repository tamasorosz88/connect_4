package hu.nye.board;

/**
 * Represents a game board for a connect-four style game.
 * The board is initialized with a specified number of rows and columns.
 * It provides methods to manipulate and query the state of the board.
 */
public class Board {
    private static final char EMPTY = '.';
    private final char[][] board;
    private final int rows;
    private final int columns;

    // ANSI color codes
    private static final String RESET = "\u001B[0m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String CLEAR_SCREEN = "\033[H\033[2J"; // Clear screen

    /**
     * Constructs a Board with the specified number of rows and columns.
     *
     * @param rows the number of rows
     * @param columns the number of columns
     * @throws IllegalArgumentException if rows or columns are not between 4 and 12
     */
    public Board(int rows, int columns) {
        if (rows < 4 || rows > 12 || columns < 4 || columns > 12) {
            throw new IllegalArgumentException("Rows and columns must be between 4 and 12.");
        }
        this.rows = rows;
        this.columns = columns;
        this.board = new char[rows][columns];
        initializeBoard();
    }

    /**
     * Initializes the board by setting all positions to EMPTY.
     */
    private void initializeBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    /**
     * Returns a copy of the current state of the board.
     *
     * @return a 2D array representing the board state
     */
    public char[][] getBoardState() {
        char[][] boardCopy = new char[rows][columns];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(board[i], 0, boardCopy[i], 0, columns);
        }
        return boardCopy;
    }

    /**
     * Prints the current state of the board to the console.
     */
    public void printBoard() {
        System.out.print(CLEAR_SCREEN); // Clear the screen
        System.out.flush(); // Ensure the clear happens immediately

        System.out.println("  " + String.join(" ", "a,b,c,d,e,f,g,h,i,j,k,l".substring(0, 2 * columns - 1).split(",")));
        for (int i = 0; i < rows; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < columns; j++) {
                if (board[i][j] == 'Y') {
                    System.out.print(YELLOW + "O" + RESET + " ");
                } else if (board[i][j] == 'R') {
                    System.out.print(RED + "O" + RESET + " ");
                } else {
                    System.out.print(board[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Converts the board state to a string array for file saving.
     *
     * @return a string array representing the board state
     */
    public String[] toStringArray() {
        String[] boardRepresentation = new String[rows];
        for (int i = 0; i < rows; i++) {
            boardRepresentation[i] = new String(board[i]);
        }
        return boardRepresentation;
    }

    /**
     * Loads the board state from a string array.
     *
     * @param lines a string array representing the board state
     */
    public void loadFromStringArray(String[] lines) {
        for (int i = 0; i < Math.min(rows, lines.length); i++) {
            char[] row = lines[i].toCharArray();
            System.arraycopy(row, 0, board[i], 0, Math.min(columns, row.length));
        }
    }

    /**
     * Checks if a column is full.
     *
     * @param col the column index
     * @return true if the column is full, false otherwise
     */
    public boolean isColumnFull(int col) {
        return board[0][col] != EMPTY; // The first row must not be empty
    }

    /**
     * Places a disc in the specified column for the given player.
     *
     * @param player the player's symbol
     * @param col the column index
     * @return true if the disc was placed successfully, false otherwise
     */
    public boolean placeDisc(char player, int col) {
        if (isColumnFull(col)) {
            return false;
        }
        for (int i = rows - 1; i >= 0; i--) {
            if (board[i][col] == EMPTY) {
                board[i][col] = player; // Place the disc in the appropriate column
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the specified player has won the game.
     *
     * @param player the player's symbol
     * @return true if the player has won, false otherwise
     */
    public boolean checkWin(char player) {
        // Horizontal check
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col <= columns - 4; col++) {
                if (board[row][col] == player &&
                        board[row][col + 1] == player &&
                        board[row][col + 2] == player &&
                        board[row][col + 3] == player) {
                    return true;
                }
            }
        }

        // Vertical check
        for (int col = 0; col < columns; col++) {
            for (int row = 0; row <= rows - 4; row++) {
                if (board[row][col] == player &&
                        board[row + 1][col] == player &&
                        board[row + 2][col] == player &&
                        board[row + 3][col] == player) {
                    return true;
                }
            }
        }

        // Diagonal (top-left to bottom-right) check
        for (int row = 0; row <= rows - 4; row++) {
            for (int col = 0; col <= columns - 4; col++) {
                if (board[row][col] == player &&
                        board[row + 1][col + 1] == player &&
                        board[row + 2][col + 2] == player &&
                        board[row + 3][col + 3] == player) {
                    return true;
                }
            }
        }

        // Diagonal (top-right to bottom-left) check
        for (int row = 0; row <= rows - 4; row++) {
            for (int col = 3; col < columns; col++) {
                if (board[row][col] == player &&
                        board[row + 1][col - 1] == player &&
                        board[row + 2][col - 2] == player &&
                        board[row + 3][col - 3] == player) {
                    return true;
                }
            }
        }

        return false; // No winner
    }

    /**
     * Checks if the board is full.
     *
     * @return true if the board is full, false otherwise
     */
    public boolean isFull() {
        for (int j = 0; j < columns; j++) {
            if (board[0][j] == EMPTY) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the number of rows in the board.
     *
     * @return the number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Returns the number of columns in the board.
     *
     * @return the number of columns
     */
    public int getColumns() {
        return columns;
    }
}
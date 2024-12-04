package hu.nye.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Board} class.
 */
public class BoardTest {
    private Board board;

    /**
     * Sets up a new Board instance before each test.
     */
    @BeforeEach
    public void setUp() {
        board = new Board(5, 7);
    }

    /**
     * Tests the {@link Board#placeDisc(char, int)} method.
     * Ensures that a disc is placed correctly in the specified column.
     */
    @Test
    public void testPlaceDisc() {
        board.placeDisc('R', 0);
        assertEquals('R', board.getBoardState()[board.getRows() - 1][0]);
    }

    /**
     * Tests the {@link Board#isColumnFull(int)} method.
     * Ensures that the method correctly identifies a full column.
     */
    @Test
    public void testIsColumnFull() {
        for (int i = 0; i < board.getRows(); i++) {
            board.placeDisc('R', 0);
        }
        assertTrue(board.isColumnFull(0));
    }

    /**
     * Tests the {@link Board#isFull()} method.
     * Ensures that the method correctly identifies a full board.
     */
    @Test
    public void testIsFull() {
        for (int i = 0; i < board.getColumns(); i++) {
            for (int j = 0; j < board.getRows(); j++) {
                board.placeDisc('R', i);
            }
        }
        assertTrue(board.isFull());
    }

    /**
     * Tests the {@link Board#checkWin(char)} method.
     * Ensures that the method correctly identifies a horizontal winning condition.
     */
    @Test
    public void testCheckWinHorizontal() {
        for (int i = 0; i < 4; i++) {
            board.placeDisc('R', i);
        }
        assertTrue(board.checkWin('R'));
    }

    /**
     * Tests the {@link Board#checkWin(char)} method.
     * Ensures that the method correctly identifies a vertical winning condition.
     */
    @Test
    public void testCheckWinVertical() {
        for (int i = 0; i < 4; i++) {
            board.placeDisc('R', 0);
        }
        assertTrue(board.checkWin('R'));
    }

    /**
     * Tests the {@link Board#checkWin(char)} method.
     * Ensures that the method correctly identifies a diagonal winning condition
     * from top-left to bottom-right.
     */
    @Test
    public void testCheckWinDiagonalTopLeftToBottomRight() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < i; j++) {
                board.placeDisc('Y', i);
            }
            board.placeDisc('R', i);
        }
        assertTrue(board.checkWin('R'));
    }

    /**
     * Tests the {@link Board#checkWin(char)} method.
     * Ensures that the method correctly identifies a diagonal winning condition
     * from top-right to bottom-left.
     */
    @Test
    public void testCheckWinDiagonalTopRightToBottomLeft() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < i; j++) {
                board.placeDisc('Y', 3 - i);
            }
            board.placeDisc('R', 3 - i);
        }
        assertTrue(board.checkWin('R'));
    }

    /**
     * Tests the {@link Board#checkWin(char)} method.
     * Ensures that the method correctly identifies when there is no winning
     * condition.
     */
    @Test
    public void testCheckWinNoWin() {
        board.placeDisc('R', 0);
        board.placeDisc('Y', 1);
        board.placeDisc('R', 2);
        board.placeDisc('Y', 3);
        assertFalse(board.checkWin('R'));
        assertFalse(board.checkWin('Y'));
    }

    /**
     * Tests the {@link Board#printBoard()} method.
     * Ensures that the method correctly prints the board state.
     */
    @Test
    public void testPrintBoard() {
        board.placeDisc('R', 0);
        board.placeDisc('Y', 1);
        board.placeDisc('R', 2);
        board.placeDisc('Y', 3);

        // Capture the printed output
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        board.printBoard();

        String expectedOutput = "\033[H\033[2J" + // Clear screen
                "  a b c d e f g\r\n" +             // Fejléc
                "1 . . . . . . . \r\n" +            // 1. sor
                "2 . . . . . . . \r\n" +            // 2. sor
                "3 . . . . . . . \r\n" +            // 3. sor
                "4 . . . . . . . \r\n" +            // 4. sor
                "5 \033[31mO\033[0m \033[33mO\033[0m \033[31mO\033[0m \033[33mO\033[0m . . . \r\n"; // 5. sor, színekkel


        assertEquals(expectedOutput, outContent.toString());
    }

    /**
     * Tests the {@link Board#loadFromStringArray(String[])} method.
     * Ensures that the method correctly loads the board state from a string array.
     */
    @Test
    public void testLoadFromStringArray() {
        String[] boardState = {
                ".....",
                "..R..",
                "..Y..",
                ".....",
                "....."
        };

        board.loadFromStringArray(boardState);

        char[][] expectedState = {
                { '.', '.', '.', '.', '.' , '.', '.' },
                { '.', '.', 'R', '.', '.' , '.', '.' },
                { '.', '.', 'Y', '.', '.' , '.', '.' },
                { '.', '.', '.', '.', '.' , '.', '.' },
                { '.', '.', '.', '.', '.' , '.', '.' }
        };

        assertArrayEquals(expectedState, board.getBoardState());
    }

    /**
     * Tests the {@link Board#loadFromStringArray(String[])} method with a different
     * size array.
     * Ensures that the method correctly loads the board state from a string array
     * of different size.
     */
    @Test
    public void testLoadFromStringArrayWithDifferentSize() {
        String[] boardState = {
                "R....",
                "Y...."
        };

        board.loadFromStringArray(boardState);

        char[][] expectedState = {
                { 'R', '.', '.', '.', '.' , '.', '.' },
                { 'Y', '.', '.', '.', '.' , '.', '.' },
                { '.', '.', '.', '.', '.' , '.', '.' },
                { '.', '.', '.', '.', '.' , '.', '.' },
                { '.', '.', '.', '.', '.' , '.', '.' }
        };

        assertArrayEquals(expectedState, board.getBoardState());
    }

    /**
     * Tests the {@link Board#loadFromStringArray(String[])} method with an empty
     * array.
     * Ensures that the method correctly handles an empty string array.
     */
    @Test
    public void testLoadFromStringArrayWithEmptyArray() {
        String[] boardState = {};

        board.loadFromStringArray(boardState);

        char[][] expectedState = {
                { '.', '.', '.', '.', '.', '.', '.' },
                { '.', '.', '.', '.', '.', '.', '.' },
                { '.', '.', '.', '.', '.', '.', '.' },
                { '.', '.', '.', '.', '.', '.', '.' },
                { '.', '.', '.', '.', '.', '.', '.' }
        };

        assertArrayEquals(expectedState, board.getBoardState());
    }
}
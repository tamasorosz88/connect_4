package hu.nye.score;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link BoardState} class.
 */
public class BoardStateTest {

    @Test
    public void testConstructorAndGetters() {
        List<List<Disc>> board = new ArrayList<>();
        board.add(Arrays.asList(new Disc("."), new Disc("."), new Disc("."), new Disc("."), new Disc(".")));
        board.add(Arrays.asList(new Disc("."), new Disc("."), new Disc("R"), new Disc("."), new Disc(".")));
        board.add(Arrays.asList(new Disc("."), new Disc("."), new Disc("Y"), new Disc("."), new Disc(".")));
        board.add(Arrays.asList(new Disc("."), new Disc("."), new Disc("."), new Disc("."), new Disc(".")));
        board.add(Arrays.asList(new Disc("."), new Disc("."), new Disc("."), new Disc("."), new Disc(".")));

        BoardState boardState = new BoardState(board);
        assertEquals(board, boardState.getBoard());
    }

    @Test
    public void testToString() {
        List<List<Disc>> board = new ArrayList<>();
        board.add(Arrays.asList(new Disc("."), new Disc("."), new Disc("."), new Disc("."), new Disc(".")));
        board.add(Arrays.asList(new Disc("."), new Disc("."), new Disc("R"), new Disc("."), new Disc(".")));
        board.add(Arrays.asList(new Disc("."), new Disc("."), new Disc("Y"), new Disc("."), new Disc(".")));
        board.add(Arrays.asList(new Disc("."), new Disc("."), new Disc("."), new Disc("."), new Disc(".")));
        board.add(Arrays.asList(new Disc("."), new Disc("."), new Disc("."), new Disc("."), new Disc(".")));

        BoardState boardState = new BoardState(board);
        String expected = "[., ., ., ., .]\n" +
                "[., ., R, ., .]\n" +
                "[., ., Y, ., .]\n" +
                "[., ., ., ., .]\n" +
                "[., ., ., ., .]\n";
        assertEquals(expected, boardState.toString());
    }
}
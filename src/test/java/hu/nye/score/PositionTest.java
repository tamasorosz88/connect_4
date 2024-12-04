package hu.nye.score;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Position} class.
 */
public class PositionTest {

    @Test
    public void testConstructorAndGetters() {
        Position position = new Position(3, 5);
        assertEquals(3, position.getRow());
        assertEquals(5, position.getColumn());
    }

    @Test
    public void testToString() {
        Position position = new Position(3, 5);
        assertEquals("3,5", position.toString());
    }
}
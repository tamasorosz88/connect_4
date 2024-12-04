package hu.nye.score;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Disc} class.
 */
public class DiscTest {

    @Test
    public void testConstructorAndGetters() {
        Disc disc = new Disc("Red");
        assertEquals("Red", disc.getColor());
    }

    @Test
    public void testToString() {
        Disc disc = new Disc("Red");
        assertEquals("Red", disc.toString());
    }
}
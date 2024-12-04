package hu.nye.score;

/**
 * The Disc class represents a disc in the game, identified by its color.
 */
public class Disc {
    private final String color;

    /**
     * Constructs a new Disc with the specified color.
     *
     * @param color the color of the disc
     */
    public Disc(String color) {
        this.color = color;
    }

    /**
     * Returns the color of the disc.
     *
     * @return the color of the disc
     */
    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return String.valueOf(color);
    }
}
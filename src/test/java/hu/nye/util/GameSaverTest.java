package hu.nye.util;

import hu.nye.board.Board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link GameSaver} class.
 */
public class GameSaverTest {

    private static final String TEST_FILE = "test_game_state.xml";
    private static final String TEST_HIGH_SCORE_FILE = "test_high_scores.xml";

    /**
     * Sets up the test environment by creating test files.
     *
     * @throws IOException if an I/O error occurs
     */
    @BeforeEach
    public void setUp() throws IOException {
        // Create a test XML file for loadBoardFromFile and saveBoardToFile tests
        try (FileWriter writer = new FileWriter(TEST_FILE)) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<board>\n");
            writer.write("  <row>.....</row>\n");
            writer.write("  <row>..R..</row>\n");
            writer.write("  <row>..Y..</row>\n");
            writer.write("  <row>.....</row>\n");
            writer.write("  <row>.....</row>\n");
            writer.write("</board>\n");
        }

        // Create a test high score file for deleteHighScoreTable tests
        try (FileWriter writer = new FileWriter(TEST_HIGH_SCORE_FILE)) {
            writer.write("High score content");
        }
    }

    /*
    @AfterEach
    public void tearDown() {
        new File(TEST_FILE).delete();
        new File(TEST_HIGH_SCORE_FILE).delete();
    }
    */


    /**
     * Tests the {@link GameSaver#getBoardDimensions(Document)} method.
     * Ensures that the method correctly retrieves the board dimensions from the XML
     * document.
     *
     * @throws Exception if an error occurs during XML parsing
     */
    @Test
    public void testGetBoardDimensions() throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new File(TEST_FILE));
        doc.getDocumentElement().normalize();

        int[] dimensions = GameSaver.getBoardDimensions(doc);
        assertEquals(5, dimensions[0]);
        assertEquals(5, dimensions[1]);
    }

    /**
     * Tests the {@link GameSaver#deleteHighScoreTable(String)} method.
     * Ensures that the method correctly deletes the high score file.
     */
    @Test
    public void testDeleteHighScoreTable() {
        File file = new File(TEST_HIGH_SCORE_FILE);
        assertTrue(file.exists());

        GameSaver.deleteHighScoreTable(TEST_HIGH_SCORE_FILE);
        assertFalse(file.exists());
    }

    /**
     * Tests the {@link GameSaver#loadBoardFromFile(String)} method.
     * Ensures that the method correctly loads the board state from the XML file.
     */
    @Test
    public void testLoadBoardFromFile() {
        Board board = GameSaver.loadBoardFromFile(TEST_FILE);
        assertNotNull(board);

        char[][] expectedState = {
                { '.', '.', '.', '.', '.' },
                { '.', '.', 'R', '.', '.' },
                { '.', '.', 'Y', '.', '.' },
                { '.', '.', '.', '.', '.' },
                { '.', '.', '.', '.', '.' }
        };

        assertArrayEquals(expectedState, board.getBoardState());
    }

    /**
     * Tests the {@link GameSaver#saveBoardToFile(Board, String)} method.
     * Ensures that the method correctly saves the board state to the XML file.
     *
     * @throws Exception if an error occurs during XML parsing
     */
    @Test
    public void testSaveBoardToFile() throws Exception {
        Board board = new Board(5, 5);
        board.placeDisc('R', 2);
        board.placeDisc('Y', 2);

        GameSaver.saveBoardToFile(board, TEST_FILE);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new File(TEST_FILE));
        doc.getDocumentElement().normalize();

        NodeList rowNodes = doc.getDocumentElement().getElementsByTagName("row");
        assertEquals(5, rowNodes.getLength());
        assertEquals(".....", rowNodes.item(0).getTextContent());
        assertEquals(".....", rowNodes.item(1).getTextContent());
        assertEquals(".....", rowNodes.item(2).getTextContent());
        assertEquals("..Y..", rowNodes.item(3).getTextContent());
        assertEquals("..R..", rowNodes.item(4).getTextContent());
    }
}
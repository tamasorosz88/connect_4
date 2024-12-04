package hu.nye.player;

import hu.nye.board.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

/**
 * Unit test for the ComputerPlayer class using Mockito to mock the Board class.
 */
public class ComputerPlayerMockTest {
    private Board board;
    private ComputerPlayer computerPlayer;

    /**
     * Sets up the test environment by initializing the mock Board and the ComputerPlayer.
     */
    @BeforeEach
    public void setUp() {
        board = Mockito.mock(Board.class);
        computerPlayer = new ComputerPlayer('R', board);
    }

    /**
     * Tests the makeMove method of the ComputerPlayer class with a mocked Board.
     * Ensures that the correct methods are called on the Board.
     */
    @Test
    public void testMakeMoveWithMock() {
        // Arrange
        when(board.getColumns()).thenReturn(7); // Ensure the number of columns is positive
        when(board.isColumnFull(anyInt())).thenReturn(false); // Ensure no column is full

        // Act
        computerPlayer.makeMove(0);

        // Assert
        verify(board, times(1)).getColumns();
        verify(board, atLeastOnce()).isColumnFull(anyInt());
        verify(board, atLeastOnce()).placeDisc(eq('R'), anyInt());
    }
}
package org.tictactoe;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }


    @Test
    void isCellEmpty_ShouldReturnTrue_OnNewBoard() {
        assertTrue(board.isCellEmpty(0, 0), "Cell should be empty initially");
    }

    @Test
    void isCellEmpty_ShouldReturnFalse_AfterPlacingMarker() {
        board.place(0, 0, 'X');
        assertFalse(board.isCellEmpty(0, 0), "Cell should not be empty after placing a marker");
    }



    @Test
    void place_ShouldCorrectlyPlaceMarker() {
        board.place(1, 1, 'O');
        assertEquals('O', board.getBoard()[1][1], "Marker should be placed in the correct cell");
    }

    @Test
    void place_ShouldOverwriteExistingMarker() {
        board.place(1, 1, 'O');
        board.place(1, 1, 'X');
        assertEquals('X', board.getBoard()[1][1], "New marker should overwrite the old one");
    }



    @Test
    void isFull_ShouldReturnFalse_OnNewBoard() {
        assertFalse(board.isFull(), "New board should not be full");
    }

    @Test
    void isFull_ShouldReturnTrue_WhenCompletelyFilled() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board.place(i, j, 'X');
            }
        }
        assertTrue(board.isFull(), "Board should be full after filling all cells");
    }



    @Test
    void clear_ShouldEmptyBoard() {
        board.place(0, 0, 'X');
        board.clear();
        assertTrue(board.isCellEmpty(0, 0), "Board should be empty after clear()");
    }

    @Test
    void clear_ShouldNotKeepOldMarkers() {
        board.place(1, 2, 'O');
        board.place(2, 2, 'X');
        board.clear();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(' ', board.getBoard()[i][j], "All cells should be reset to empty");
            }
        }
    }



    @Test
    void getBoard_ShouldReturnSameReference() {
        assertSame(board.getBoard(), board.getBoard(), "getBoard should return the same reference each time");
    }

    @Test
    void getBoard_ShouldReflectPlacedMarkers() {
        board.place(2, 2, 'X');
        assertEquals('X', board.getBoard()[2][2], "getBoard should reflect changes made via place()");
    }
}

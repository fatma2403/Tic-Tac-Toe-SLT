package org.tictactoe;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void constructorAndGetMarker_shouldReturnCorrectMarker_X() {
        Player player = new Player('X');
        assertEquals('X', player.getMarker(),
                "Player should return the marker passed in constructor");
    }

    @Test
    void constructorAndGetMarker_shouldReturnCorrectMarker_O() {
        Player player = new Player('O');
        assertEquals('O', player.getMarker(),
                "Player should return the marker passed in constructor");
    }


    @Test
    void constructor_shouldAcceptUnexpectedMarker() {
        Player player = new Player('#'); // invalid in game logic
        assertEquals('#', player.getMarker(),
                "Even unexpected markers should be stored as-is (no validation currently)");
    }
}

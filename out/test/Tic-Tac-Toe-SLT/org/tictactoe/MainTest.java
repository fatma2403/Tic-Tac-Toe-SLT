package org.tictactoe;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    void mainRunsWithoutError() {
        assertDoesNotThrow(() -> Main.main(new String[]{}));
    }
}
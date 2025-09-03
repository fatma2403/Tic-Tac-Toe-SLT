package org.tictactoe;

import org.junit.jupiter.api.*;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class TicTacToeTest {

    private InputStream originalIn;
    private PrintStream originalOut;

    @BeforeEach
    void saveIO() {
        originalIn = System.in;
        originalOut = System.out;
    }

    @AfterEach
    void restoreIO() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }


    private static Field getField(Class<?> c, String name) throws Exception {
        Field f = c.getDeclaredField(name);
        f.setAccessible(true);
        return f;
    }

    private static Method getMethod(Class<?> c, String name, Class<?>... params) throws Exception {
        Method m = c.getDeclaredMethod(name, params);
        m.setAccessible(true);
        return m;
    }

    private static Board getBoard(TicTacToe ttt) throws Exception {
        Field f = getField(TicTacToe.class, "board");
        return (Board) f.get(ttt);
    }

    private static Object getCurrentPlayer(TicTacToe ttt) throws Exception {
        Field f = getField(TicTacToe.class, "currentPlayer");
        return f.get(ttt);
    }

    private static Object getPlayer1(TicTacToe ttt) throws Exception {
        Field f = getField(TicTacToe.class, "player1");
        return f.get(ttt);
    }

    private static Object getPlayer2(TicTacToe ttt) throws Exception {
        Field f = getField(TicTacToe.class, "player2");
        return f.get(ttt);
    }

    private static char markerOf(Object player) throws Exception {
        Field m = player.getClass().getDeclaredField("marker");
        m.setAccessible(true);
        return (char) m.get(player);
    }


    @Test
    void start_shouldRunGameAndDeclareWinner_positive() throws Exception {
        // X gewinnt Top-Reihe: (0,0) (0,1) (0,2)
        String scripted = String.join("\n",
                "0","0",   // X
                "1","1",   // O
                "0","1",   // X
                "2","2",   // O
                "0","2",   // X -> gewinnt
                "n"        // nicht nochmal
        ) + "\n";

        ByteArrayInputStream in = new ByteArrayInputStream(scripted.getBytes());
        ByteArrayOutputStream outBuf = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(outBuf));

        TicTacToe ttt = new TicTacToe();
        ttt.start();

        String out = outBuf.toString();
        assertTrue(out.contains("won"), "Should announce a winner");
        assertTrue(out.toLowerCase().contains("thanks for playing"), "Should say goodbye at end");
    }


    @Test
    void start_shouldHandleInvalidInputs_negative() {
        // Ungültige Eingaben: "x", "3" (out-of-range), dann gültig; plus Abbruch "n"
        String scripted = String.join("\n",
                "x",        // invalid char for row
                "3",        // out-of-range row
                "0",        // valid row
                "y",        // invalid char for col
                "-1",       // out-of-range col
                "0",        // valid col -> X at (0,0)
                "1","1",    // O
                "0","1",    // X
                "2","2",    // O
                "0","2",    // X wins
                "n"
        ) + "\n";

        ByteArrayInputStream in = new ByteArrayInputStream(scripted.getBytes());
        ByteArrayOutputStream outBuf = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(outBuf));

        TicTacToe ttt = new TicTacToe();
        ttt.start();

        String out = outBuf.toString();
        assertTrue(out.toLowerCase().contains("invalid input"), "Should warn on non-numeric");
        assertTrue(out.toLowerCase().contains("between 0 and 2"), "Should warn on out-of-range");
        assertTrue(out.contains("won"), "Game should still proceed to a win");
    }


    @Test
    void start_shouldDetectDraw() {
        String scripted = String.join("\n",
                "0","0", // X
                "0","1", // O
                "0","2", // X
                "1","2", // O
                "1","0", // X
                "2","0", // O
                "1","1", // X
                "2","2", // O
                "2","1", // X -> full, draw
                "n"
        ) + "\n";

        ByteArrayInputStream in = new ByteArrayInputStream(scripted.getBytes());
        ByteArrayOutputStream outBuf = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(outBuf));

        TicTacToe ttt = new TicTacToe();
        ttt.start();

        String out = outBuf.toString();
        assertTrue(out.toLowerCase().contains("draw") || out.toLowerCase().contains("no more moves"),
                "Should report a draw when board is full without winner");
    }


    @Test
    void hasWinner_row_col_diag_positive() throws Exception {
        TicTacToe ttt = new TicTacToe();
        Board b = getBoard(ttt);
        Method hasWinner = getMethod(TicTacToe.class, "hasWinner");

        // Row win
        b.clear();
        b.place(0,0,'X'); b.place(0,1,'X'); b.place(0,2,'X');
        assertTrue((boolean) hasWinner.invoke(ttt));

        // Column win
        b.clear();
        b.place(0,1,'O'); b.place(1,1,'O'); b.place(2,1,'O');
        assertTrue((boolean) hasWinner.invoke(ttt));

        // Main diagonal
        b.clear();
        b.place(0,0,'X'); b.place(1,1,'X'); b.place(2,2,'X');
        assertTrue((boolean) hasWinner.invoke(ttt));

        // Anti-diagonal
        b.clear();
        b.place(0,2,'O'); b.place(1,1,'O'); b.place(2,0,'O');
        assertTrue((boolean) hasWinner.invoke(ttt));
    }

    @Test
    void hasWinner_noWin_negative() throws Exception {
        TicTacToe ttt = new TicTacToe();
        Board b = getBoard(ttt);
        Method hasWinner = getMethod(TicTacToe.class, "hasWinner");

        b.clear();
        b.place(0,0,'X');
        b.place(0,1,'O');
        b.place(1,1,'X');
        b.place(2,2,'O');
        // Kein Dreier
        assertFalse((boolean) hasWinner.invoke(ttt));
    }


    @Test
    void switchtCurrentPlayer_togglesBothWays() throws Exception {
        TicTacToe ttt = new TicTacToe();
        Method switcher = getMethod(TicTacToe.class, "switchtCurrentPlayer");

        Object p1 = getPlayer1(ttt);
        Object p2 = getPlayer2(ttt);

        Field cur = getField(TicTacToe.class, "currentPlayer");

        // p1 -> p2
        cur.set(ttt, p1);
        switcher.invoke(ttt);
        assertSame(p2, getCurrentPlayer(ttt));
        // p2 -> p1
        switcher.invoke(ttt);
        assertSame(p1, getCurrentPlayer(ttt));
    }


    @Test
    void getValidInput_returnsValidNumber_positive() throws Exception {
        String feed = "2\n";
        Scanner sc = new Scanner(new ByteArrayInputStream(feed.getBytes()));
        ByteArrayOutputStream outBuf = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outBuf));

        TicTacToe ttt = new TicTacToe();
        Method m = getMethod(TicTacToe.class, "getValidInput", Scanner.class, String.class);
        int v = (int) m.invoke(ttt, sc, "row (0-2): ");
        assertEquals(2, v);
    }

    @Test
    void getValidInput_handlesInvalidThenValid_negative() throws Exception {
        // invalid: "x", "5" ; then valid: "1"
        String feed = String.join("\n", "x", "5", "1") + "\n";
        Scanner sc = new Scanner(new ByteArrayInputStream(feed.getBytes()));
        ByteArrayOutputStream outBuf = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outBuf));

        TicTacToe ttt = new TicTacToe();
        Method m = getMethod(TicTacToe.class, "getValidInput", Scanner.class, String.class);
        int v = (int) m.invoke(ttt, sc, "column (0-2): ");

        String out = outBuf.toString().toLowerCase();
        assertEquals(1, v, "Should finally return the valid number");
        assertTrue(out.contains("invalid input") || out.contains("please enter"),
                "Should print an error for invalid inputs");
    }
}

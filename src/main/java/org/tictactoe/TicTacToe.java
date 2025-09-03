package org.tictactoe;

public class TicTacToe {
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Board board;

    public TicTacToe() {
        player1 = new Player('X');
        player2 = new Player('O');
        currentPlayer = player1;
        board = new Board();
    }

    public void start() {
    }

    private void switchtCurrentPlayer() {

    }
    private boolean hasWinner() {
        return false;
    }
}

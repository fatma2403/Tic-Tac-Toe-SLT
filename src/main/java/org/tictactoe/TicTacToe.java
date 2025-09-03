package org.tictactoe;

import java.util.Scanner;

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
        Scanner sc = new Scanner(System.in);

        boolean playing = true;
        while (playing) {
            board.clear();
            currentPlayer = player1;

            while (true) {
                System.out.println("Current Player " + currentPlayer.getMarker());
                board.print();

                int x = getValidInput(sc, "row (0-2): ");
                int y = getValidInput(sc, "column (0-2): ");

                if (!board.isCellEmpty(x, y)) {
                    System.out.println("Cell already occupied. Try again!");
                    continue;
                }

                board.place(x, y, currentPlayer.getMarker());

                if (hasWinner()) {
                    board.print();
                    System.out.println("Congratulations! Player " + currentPlayer.getMarker() + " won!");
                    break;
                }

                if (board.isFull()) {
                    board.print();
                    System.out.println("It's a draw! No more moves possible.");
                    break;
                }

                switchtCurrentPlayer();
            }

            System.out.print("Do you want to play again? (y/n): ");
            String answer = sc.next().trim().toLowerCase();
            if (!answer.equals("y")) {
                System.out.println("Thanks for playing!");
                playing = false;
            }
        }
    }


    private void switchtCurrentPlayer() {
        if(currentPlayer == player1) {
            currentPlayer = player2;
        }
        else if(currentPlayer == player2) {
            currentPlayer = player1;
        }
    }
    private boolean hasWinner() {
        for (int i = 0; i < 3; i++) {
            if (board.getBoard()[i][0] != ' ' &&
                    board.getBoard()[i][0] == board.getBoard()[i][1] &&
                    board.getBoard()[i][1] == board.getBoard()[i][2]) {
                return true;
            }
        }

        for (int j = 0; j < 3; j++) {
            if (board.getBoard()[0][j] != ' ' &&
                    board.getBoard()[0][j] == board.getBoard()[1][j] &&
                    board.getBoard()[1][j] == board.getBoard()[2][j]) {
                return true;
            }
        }

        if (board.getBoard()[0][0] != ' ' &&
                board.getBoard()[0][0] == board.getBoard()[1][1] &&
                board.getBoard()[1][1] == board.getBoard()[2][2]) {
            return true;
        }

        if (board.getBoard()[0][2] != ' ' &&
                board.getBoard()[0][2] == board.getBoard()[1][1] &&
                board.getBoard()[1][1] == board.getBoard()[2][0]) {
            return true;
        }

        return false;
    }

    private int getValidInput(Scanner sc, String prompt) {
        int value;
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                value = sc.nextInt();
                if (value >= 0 && value <= 2) {
                    return value;
                } else {
                    System.out.println("Please enter a number between 0 and 2.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                sc.next();
            }
        }
    }
}

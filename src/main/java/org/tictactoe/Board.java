package org.tictactoe;

import java.util.Arrays;

public class Board {
    private char[][] board;
    public Board() {
        board = new char[3][3];
        for (char[] chars : board) {
            Arrays.fill(chars, ' ');
        }
    }
    public char[][] getBoard() {
        return board;
    }
    public boolean isCellEmpty(int x, int y){
        return board[x][y] == ' ';
    }
    public void place(int x, int y, char marker){
        board[x][y] = marker;
    }
    public boolean isFull() {
        return Arrays.stream(board)         // stream of rows
                .flatMapToInt(row -> new String(row).chars())
                .noneMatch(ch -> ch == ' ');
    }
    public void clear(){

        board = new char[3][3];
        for (char[] chars : board) {
            Arrays.fill(chars, ' ');
        }
    }
    public void print(){
        System.out.println("───────");
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                System.out.print("|");
                System.out.print(board[i][j]);
                if(j == 2){
                    System.out.print("|\n");
                }
            }
        }
        System.out.println("───────");
    }
}

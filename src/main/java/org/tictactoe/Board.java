package org.tictactoe;

public class Board {
    private char[][] board;
    public Board() {
        board = new char[3][3];
    }
    public boolean isCellEmpty(int x, int y){
        return board[x][y] == ' ';
    }
    public void place(int x, int y, char marker){
        board[x][y] = marker;
    }
    public boolean isFull(){
        return false;
    }
    public void clear(){
        board = new char[3][3];
    }
    public void print(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                System.out.print(board[i][j]);
            }
        }
    }
}

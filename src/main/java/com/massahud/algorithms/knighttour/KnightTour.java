package com.massahud.algorithms.knighttour;

public class KnightTour {
    private static final int[][] POSSIBLE_MOVES = new int[][] {
            {2,1},
            {1,2},
            {-1,2},
            {-2,1},
            {-2,-1},
            {-1,-2},
            {1,-2},
            {2,-1}
    };


    int[][] board;

    public int[][] solve(int rows, int cols) {
        board = new int[rows][cols];

        solve(1,0,0);

        return board;
    }

    private boolean solve(int pos, int row, int col) {
        //System.out.println((board.length*board[0].length)+1);
        // base case
        if (pos == (board.length*board[0].length)+1) {
            return row == 0 && col == 0;
        }

        if (board[row][col] == 0) {
            board[row][col] = pos;

            for (int[] move : POSSIBLE_MOVES) {
                int nextRow = row + move[0];
                int nextCol = col + move[1];

                if (isSafe(nextRow, nextCol)) {
                    if (solve(pos + 1, nextRow, nextCol)) {
                        return true;
                    }
                }
            }
            board[row][col] = 0;
        }

        return false;
    }

    public static void printBoard(int[][] board) {
        for(int[] row: board) {
            for (int cel : row) {
                System.out.printf("%4d", cel);
            }
            System.out.println();
        }
    }

    public boolean isSafe(int row, int col) {
        if (row < 0 || row >= board.length
                || col < 0 || col >= board[0].length) {
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        KnightTour knightTour = new KnightTour();
        printBoard(knightTour.solve(6,6));
    }

}

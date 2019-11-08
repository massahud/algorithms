package com.massahud.algorithms.nqueens;

/**
 * N-Queens is the problem to plane N chess queens into a NxN board without
 * in a way that no queen threats another one.
 * This implementation uses recursion and backtracking to find the solution.
 */
public class NQueens {

    /**
     * Tries to place N queens into an NxN chess board
     *
     * @param n number of queens
     * @return a solution array, where the indexes are the columns of the board, and the value is the row that
     * the queen should be placed in that column.
     */
    public int[] placeQueens(int n) {

        int[] solution = new int[n];

        placeQueens(0, solution);
        return solution;
    }

    /**
     * Recursive method that tries to place the queen on column 'column', and then passes the current partial solution
     * to it again, until all columns are solved.
     * @param column the column that should be solved.
     * @param solution the partial solution until now.
     * @return true if it solved the placement problem, false otherwise.
     */
    private boolean placeQueens(int column, int[] solution) {
        // base case, all columns are ok
        if (column == solution.length) {
            return true;
        }
        for (int row = 0; row < solution.length; row++) {
            solution[column] = row;
            if (noThreats(solution, column)) {
                if (placeQueens(column+1, solution)) {
                    return true;
                }
            }
        }
        solution[column] = -1;
        return false;
    }

    /**
     * This methods check the queens placement for column 'column'
     * @param solution current partial solution
     * @param column column index that should be verified against all previous columns
     * @return true if there are no threats to 'column' queen, false otherwise.
     */
    private boolean noThreats(int[] solution, int column) {
        for (int i = 0; i < column; i++) {
            if (solution[i] == solution[column]) {
                return false;
            }
            int diff = column - i;
            if (solution[i] + diff == solution[column]) {
                return false;
            }
            if (solution[i] - diff == solution[column]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Prints a solution to System.out with ascii drawing
     * @param solution
     */
    public static void printSolution(int[] solution) {
         for (int i = 0; i < solution.length; i++) {
             System.out.print(" _");
         }
         System.out.println();
         for (int i = 0; i < solution.length; i++) {
             for (int j = 0; j < solution.length; j++) {
                 System.out.print("|");
                 if (i == solution[j]) {
                     System.out.print("@");
                 } else {
                     System.out.print("_");
                 }
             }
             System.out.println("|");
         }
    }

    public static void main(String[] args) {
        NQueens nQueens = new NQueens();
        printSolution(nQueens.placeQueens(40));
    }

}

package tictactoe;

public class GameBoard {
    private static final int SIZE = 3;
    private static final char X = 'X';
    private static final char O = 'O';
    private static final char EMPTY = '_';

    public char[][] gameField;

    public static int getSIZE() {
        return SIZE;
    }

    /**
     * Make empty game field
     */
    public void initField() {
        gameField = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE * SIZE; i++) {
            gameField[i / SIZE][i % SIZE] = EMPTY;
        }
    }

    /**
     * Print game field
     */
    public void printField() {
        System.out.println("---------");
        for (int i = 0; i < SIZE; i++) {
            System.out.print("| ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(gameField[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    /**
     * Check possible WIN or DRAW or Game not finished
     * @return int 1 - if X win, 2 - if O win, 3 - if Draw, 0 - if not finished
     */
    public int checkField() {
        //check win & impossible win
        if (checkDiagWin(X) || checkRowColWin(X)) {
            return 1; // X wins
        } else if (checkDiagWin(O) || checkRowColWin(O)) {
            return 2; // O wins
        }

        //check draw or not finished
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (gameField[i][j] == ' ' || gameField[i][j] == '_') {
                    return 0; // Not finished
                }
            }
        }
        return 3; // Draw
    }

    /**
     * Diagonal win?
     * @param symbol - check this symbol
     * @return true if WIN
     */
    private boolean checkDiagWin(char symbol) {
        boolean leftRightDiag = true;
        boolean rightLeftDiag = true;

        for (int i = 0; i < SIZE; i++) {
            leftRightDiag &= (gameField[i][i] == symbol);
            rightLeftDiag &= (gameField[SIZE - i - 1][i] == symbol);
        }

        return leftRightDiag || rightLeftDiag;
    }

    /**
     * ROW or COLUMN WIN?
     * @param symbol - check this symbol
     * @return true if WIN
     */
    private boolean checkRowColWin(char symbol) {
        boolean cols, rows;

        for (int col = 0; col < SIZE; col++) {
            cols = true;
            rows = true;

            for (int row = 0; row < SIZE; row++) {
                cols &= (gameField[col][row] == symbol);
                rows &= (gameField[row][col] == symbol);
            }
            if (cols || rows) {
                return true;
            }
        }
        return false;
    }

}

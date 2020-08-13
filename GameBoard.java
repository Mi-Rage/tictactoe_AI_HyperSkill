package tictactoe;

import java.util.Scanner;

public class GameBoard {
    private static final int SIZE = 3;
    private static final char X = 'X';
    private static final char O = 'O';

    public char[][] gameField;
    private static Scanner scanner = new Scanner(System.in);

    public static int getSIZE() {
        return SIZE;
    }

    public char initField() {
        System.out.print("Enter cells:");

        String cells = scanner.nextLine();

        gameField = new char[SIZE][SIZE];

        int count = 0;
        int countX = 0;
        int countO = 0;

        for (int i = 0; i < SIZE * SIZE; i++) {
            gameField[i / SIZE][i % SIZE] = cells.charAt(count);
            if (cells.charAt(count) == X) {
                countX++;
            } else if(cells.charAt(count) == O) {
                countO++;
            }
            count++;
        }

        return countX > countO ? O : X;
    }

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

    public boolean checkField() {

        //check win & impossible win
        if (checkDiagWin(X) || checkRowColWin(X)) {
            System.out.println("X wins");
            return true;
        } else if (checkDiagWin(O) || checkRowColWin(O)) {
            System.out.println("O wins");
            return true;
        }

        //check draw or not finished
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (gameField[i][j] == ' ' || gameField[i][j] == '_') {
                    System.out.println("Game not finished");
                    return true;
                }
            }
        }
        System.out.println("Draw");
        return true;
    }

    private boolean checkDiagWin(char symbol) {
        boolean leftRightDiag = true;
        boolean rightLeftDiag = true;

        for (int i = 0; i < SIZE; i++) {
            leftRightDiag &= (gameField[i][i] == symbol);
            rightLeftDiag &= (gameField[SIZE - i - 1][i] == symbol);
        }

        if (leftRightDiag || rightLeftDiag) {
            System.out.println(symbol + " wins");
            return true;
        }
        return false;
    }

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

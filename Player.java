package tictactoe;

import java.util.Scanner;

public class Player {

    public static Scanner scanner = new Scanner(System.in);
    private static final char X = 'X';
    private static final char O = 'O';

    public void makeTurn(GameBoard gameBoard, char symbol) {

        while (true) {
            int x;
            int y;
            System.out.print("Enter the coordinates: ");

            //check symbols without numbers
            while (true) {
                String turn = scanner.nextLine();
                if (!turn.matches("\\d\\s\\d")) {
                    System.out.println("You should enter numbers!");
                    System.out.print("Enter the coordinates: ");
                } else {
                    x = Integer.parseInt(String.valueOf(turn.charAt(0))) - 1;
                    y = Integer.parseInt(String.valueOf(turn.charAt(2))) - 1;
                    break;
                }
            }

            // possible make turn
            if (x < 0 || x > GameBoard.getSIZE() - 1 || y < 0 || y > GameBoard.getSIZE() - 1) {
                System.out.println("Coordinates should be from 1 to 3!");
            } else if (gameBoard.gameField[x][y] == X || gameBoard.gameField[x][y] == O) {
                System.out.println("This cell is occupied! Choose another one!");
            } else {
                gameBoard.gameField[x][y] = symbol;
                break;
            }
        }
    }

}

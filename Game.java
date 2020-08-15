package tictactoe;

import java.util.Scanner;

public class Game {
    public static final char X = 'X';
    private static final char O = 'O';
    private static final Scanner scanner = new Scanner(System.in);
    private static String[] command;
    private int result = 0;

    public void start() {
        GameBoard gameBoard = new GameBoard();


        while (true) {

            getCommand();
            if (command[0].equals("exit")) {
                System.exit(0);
            }

            Player player1 = new Player(X, command[1]);
            Player player2 = new Player(O, command[2]);

            gameBoard.initField();
            gameBoard.printField();

            while (true) {
                player1.makeTurn(gameBoard);
                gameBoard.printField();
                result = gameBoard.checkField();
                if (result != 0) {
                    break;
                }
                player2.makeTurn(gameBoard);
                gameBoard.printField();
                result = gameBoard.checkField();
                if (result != 0) {
                    break;
                }
            }
            printResult(result);
        }
    }

    public static void getCommand() {
        System.out.print("Input command: ");

        while (true) {
            command = scanner.nextLine().split(" ");
            if (command[0].equals("exit")) {
                break;
            }
            if (command.length < 3) {
                System.out.println("Bad parameters!");
            } else {
                break;
            }
        }
    }

    public static void printResult(int result) {
        switch (result) {
            case 1 :
                System.out.println("X wins");
                break;
            case 2 :
                System.out.println("O wins");
                break;
            case 3 :
                System.out.println("Draw");
                break;
        }
    }


}

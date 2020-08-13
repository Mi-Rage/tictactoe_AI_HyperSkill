package tictactoe;

import java.util.Scanner;

public class Player {

    public static Scanner scanner = new Scanner(System.in);
    private static final char X = 'X';
    private static final char O = 'O';
    private static final int HUMAN = 0;
    private static final int AI_EASY = 1;


    /**
     * Make a move on the playing field with the player's symbol
     * and the selected level of difficulty
     * @param gameBoard - this game board
     * @param symbol - symbol of player
     * @param level - 0 - HUMAN turn, or AI turn
     */
    public void makeTurn(GameBoard gameBoard, char symbol, int level) {
        switch (level) {
            case HUMAN:
                humanTurn(gameBoard, symbol, level);
                break;
            case AI_EASY:
                easyAiTurn(gameBoard, symbol, level);
                break;
        }
    }

    /**
     * Make a move on the playing field of HUMAN
     * @param gameBoard - this game board
     * @param symbol - symbol of player
     * @param level - 0 - HUMAN turn, for output of error messages of turn
     */
    public void humanTurn(GameBoard gameBoard, char symbol, int level) {
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

            if (isPossibleTurn(gameBoard, x, y, level)) {
                gameBoard.gameField[x][y] = symbol;
                break;
            }
        }
    }

    /**
     * We check whether it is possible to descend on the transmitted
     * coordinates and if necessary, output a message
     * @param gameBoard - this game board
     * @param x - coordinates to check
     * @param y - coordinates to check
     * @param level - 0 - HUMAN turn, else AI level
     * @return - passed onr not coordinates
     */
    public boolean isPossibleTurn(GameBoard gameBoard, int x, int y, int level) {
        if (x < 0 || x > GameBoard.getSIZE() - 1 || y < 0 || y > GameBoard.getSIZE() - 1) {
            if (level == HUMAN) {
                System.out.println("Coordinates should be from 1 to 3!");
            }
            return false;
        } else if (gameBoard.gameField[x][y] == X || gameBoard.gameField[x][y] == O) {
            if (level == HUMAN) {
                System.out.println("This cell is occupied! Choose another one!");
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * Make a move on the playing field of AI EASY
     * Use random coordinates
     * @param gameBoard - this game board
     * @param symbol - symbol of player
     * @param level - for disable output in isPossibleTurn()
     */
    public void easyAiTurn(GameBoard gameBoard, char symbol, int level) {
        System.out.println("Making move level \"easy\"");
        while (true) {
            int randomX = (int) (Math.random() * GameBoard.getSIZE());
            int randomY = (int) (Math.random() * GameBoard.getSIZE());

            if (isPossibleTurn(gameBoard, randomX, randomY, level)) {
                gameBoard.gameField[randomX][randomY] = symbol;
                break;
            }
        }
    }

}

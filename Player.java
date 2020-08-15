package tictactoe;

import java.util.Scanner;

public class Player {

    public static Scanner scanner = new Scanner(System.in);
    private static final char X = 'X';
    private static final char O = 'O';
    private static final char EMPTY = '_';

    private final char symbol;
    private final String level;

    /**
     * Object Player constructor with her player symbol and difficulty level
     *
     * @param symbol - this player symbol (X or O)
     * @param level  - difficulty level (as String "easy", "medium" or "hard")
     */
    public Player(char symbol, String level) {
        this.symbol = symbol;
        this.level = level;
    }

    /**
     * Make a move on the playing field with the selected level of difficulty
     *
     * @param gameBoard - this game board
     */
    public void makeTurn(GameBoard gameBoard) {
        switch (level) {
            case "user":
                humanTurn(gameBoard);
                break;
            case "easy":
                easyAiTurn(gameBoard);
                break;
            case "medium":
                mediumAiTurn(gameBoard);
                break;
            default:
                System.out.println("[PLAYER] Wrong level!");
        }
    }

    /**
     * Make a move on the playing field of user as human
     *
     * @param gameBoard - this game board
     */
    public void humanTurn(GameBoard gameBoard) {
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

            if (isPossibleTurn(gameBoard, x, y)) {
                gameBoard.gameField[x][y] = symbol;
                break;
            }
        }
    }

    /**
     * We check whether it is possible to descend on the transmitted
     * coordinates and if necessary, output a message
     *
     * @param gameBoard - this game board
     * @param x         - coordinates to check
     * @param y         - coordinates to check
     * @return - boolean, passed onr not coordinates
     */
    public boolean isPossibleTurn(GameBoard gameBoard, int x, int y) {
        if (x < 0 || x > GameBoard.getSIZE() - 1 || y < 0 || y > GameBoard.getSIZE() - 1) {
            if (level.equals("user")) {
                System.out.println("Coordinates should be from 1 to 3!");
            }
            return false;
        } else if (gameBoard.gameField[x][y] == X || gameBoard.gameField[x][y] == O) {
            if (level.equals("user")) {
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
     *
     * @param gameBoard - this game board
     */
    public void easyAiTurn(GameBoard gameBoard) {
        System.out.println("Making move level \"easy\"");
        this.randomTurn(gameBoard);
    }

    /**
     * Getting random coordinates for turn AI
     *
     * @param gameBoard - this game board
     */
    public void randomTurn(GameBoard gameBoard) {
        while (true) {
            int randomX = (int) (Math.random() * GameBoard.getSIZE());
            int randomY = (int) (Math.random() * GameBoard.getSIZE());

            if (isPossibleTurn(gameBoard, randomX, randomY)) {
                gameBoard.gameField[randomX][randomY] = symbol;
                break;
            }
        }
    }

    /**
     * Make turn with medium AI.
     * Looking for the best turn and if it is not - turn randomly
     * @param gameBoard - this game board
     */
    public void mediumAiTurn(GameBoard gameBoard) {
        if (!findMediumTurn(gameBoard)) {
            randomTurn(gameBoard);
        }
    }

    /**
     * Check each cell for a possible win or an opponent's win.
     * @param gameBoard this game board
     * @return true if found best turn
     */
    public boolean findMediumTurn(GameBoard gameBoard) {
        int x = -1;
        int y = -1;
        boolean foundWin = false;

        System.out.println("Making move level \"medium\"");

        for (int i = 0; i < GameBoard.getSIZE(); i++) {
            for (int j = 0; j < GameBoard.getSIZE(); j++) {
                if (gameBoard.gameField[i][j] == EMPTY) {
                    gameBoard.gameField[i][j] = symbol;

                    if (gameBoard.checkField() != 0) {
                        x = i;
                        y = j;
                        foundWin = true;
                        break;
                    }
                    gameBoard.gameField[i][j] = EMPTY;
                }
            }
            if (foundWin) {
                break;
            }
        }
        if (foundWin) {
            gameBoard.gameField[x][y] = symbol;
            return true;
        }
        return false;
    }
}

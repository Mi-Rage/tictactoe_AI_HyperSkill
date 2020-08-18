package tictactoe;

import java.util.ArrayList;
import java.util.List;
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
            case "hard":
                hardAiTurn(gameBoard);
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
     * Check each cell for a possible win with check opponent's win.
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
                    gameBoard.gameField[i][j] = (symbol == X) ? O : X;
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

    /**
     * Check all possible moves using the minimax algorithm
     * @param gameBoard - this game board
     */
    public void hardAiTurn(GameBoard gameBoard) {
        System.out.println("Making move level \"hard\"");

        Move bestMove = minimax(gameBoard.gameField, symbol, symbol);
        gameBoard.gameField[bestMove.index[0]][bestMove.index[1]] = symbol;
    }

    /**
     * Implementation of the minimax algorithm
     * @param gameField - the state of the playing field where we are looking for the best move
     * @param callingPlayer - character of the player being checked
     * @param currentPlayer - symbol of current player
     * @return move object - the best move in this state of the playing field
     */
    public Move minimax(char[][] gameField, char callingPlayer, char currentPlayer) {
        List<Move> moves = new ArrayList<>();
        char enemySymbol = (callingPlayer == X) ? O : X;
        char callingSymbol = (callingPlayer == X) ? X : O;
        char enemyPlayer = (currentPlayer == X) ? O : X;

        // Counting the score of this move
        if (isWin(gameField, enemySymbol)) {
            return new Move(-10);
        } else if (isWin(gameField, callingSymbol)) {
            return new Move(10);
        } else if (!isEmptyCellsLeft(gameField)) {
            return new Move(0);
        }

        for (int i = 0; i < GameBoard.getSIZE(); i++) {
            for (int j = 0; j < GameBoard.getSIZE(); j++) {
                if (gameField[i][j] == EMPTY) {
                    // let's make a possible move
                    Move move = new Move();
                    move.index = new int[]{i, j};
                    gameField[i][j] = currentPlayer;
                    Move result = minimax(gameField, callingPlayer, enemyPlayer);
                    // save the score for the minimax
                    move.score = result.score;
                    // then revert the occupied place back to empty, so next guesses can go on
                    gameField[i][j] = EMPTY;
                    moves.add(move);
                }
            }
        }

        // Choose the move with the highest score
        int bestMove = 0;

        if (currentPlayer == callingPlayer) {
            int bestScore = -10000;
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i).score > bestScore) {
                    bestScore = moves.get(i).score;
                    bestMove = i;
                }
            }
        } else {
            int bestScore = 10000;
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i).score < bestScore) {
                    bestScore = moves.get(i).score;
                    bestMove = i;
                }
            }
        }

        // minimax returns the best move to the latest function caller
        return moves.get(bestMove);
    }

    /**
     * Check is empty cells left on this game board
     * @param gameField - this game board
     * @return boolean true if got empty cell
     */
    private static boolean isEmptyCellsLeft(char[][] gameField) {
        boolean gotEmptiesCells = false;
        for (int i = 0; i < GameBoard.getSIZE(); i++) {
            for (int j = 0; j < GameBoard.getSIZE(); j++) {
                if (gameField[i][j] == EMPTY) {
                    gotEmptiesCells = true;
                    break;
                }
            }
        }
        return gotEmptiesCells;
    }


    /**
     * Check possible win on this game board for this player
     * @param gameField - this game board
     * @param playerSymbol - checked player symbol
     * @return boolean true if player win
     */
    private static boolean isWin(char[][] gameField, char playerSymbol) {
        boolean leftRightDiag = true;
        boolean rightLeftDiag = true;

        for (int i = 0; i < GameBoard.getSIZE(); i++) {
            leftRightDiag &= (gameField[i][i] == playerSymbol);
            rightLeftDiag &= (gameField[GameBoard.getSIZE() - i - 1][i] == playerSymbol);
        }

        boolean cols = false;
        boolean rows = false;

        for (int col = 0; col < GameBoard.getSIZE(); col++) {
            cols = true;
            rows = true;

            for (int row = 0; row < GameBoard.getSIZE(); row++) {
                cols &= (gameField[col][row] == playerSymbol);
                rows &= (gameField[row][col] == playerSymbol);
            }
            if (cols || rows) {
                break;
            }
        }

        return leftRightDiag || rightLeftDiag || cols || rows;
    }
}



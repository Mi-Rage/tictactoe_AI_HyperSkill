package tictactoe;

public class Game {
    public static final char X = 'X';
    private static final char O = 'O';
    private static final int HUMAN = 0;
    private static final int AI_EASY = 1;

    public void start() {
        GameBoard gameBoard = new GameBoard();
        Player player = new Player();

        char playerSymbol = X;
        int playerLevel = HUMAN;

        gameBoard.initField();
        gameBoard.printField();
        while (true) {
            player.makeTurn(gameBoard, playerSymbol, playerLevel);
            gameBoard.printField();
            if (gameBoard.checkField()) {
                break;
            } else {
                playerSymbol = (playerSymbol == X ? O : X);
                playerLevel = (playerLevel == HUMAN ? AI_EASY : HUMAN);
            }
        }
    }
}

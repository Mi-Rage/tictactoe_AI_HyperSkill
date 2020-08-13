package tictactoe;

public class Game {
    private char playerSymbol;

    public void start() {
        GameBoard gameBoard = new GameBoard();
        playerSymbol = gameBoard.initField();
        gameBoard.printField();
        Player player = new Player();
        player.makeTurn(gameBoard, playerSymbol);
        gameBoard.printField();
        gameBoard.checkField();

    }
}

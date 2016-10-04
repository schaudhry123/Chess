package main;

import main.model.*;
import main.pieces.Ferz;
import main.pieces.Piece;
import main.pieces.Princess;

import javax.swing.JOptionPane;

public class GameController {

    public static int length = 8;
    public static int width = 8;

    private int turn = 0;

    private Board board;
    private BoardView boardView;
    private Player whitePlayer;
    private Player blackPlayer;

    private Piece selectedPiece = null;
    protected Coordinate startCoordinate = null;

    private Move lastMove = null;

    // ===== GameController State Booleans =====
    private boolean gameStarted      = false;
    private boolean gameQuitted      = false;
    private boolean gameCustom      = false;

    private boolean stopCurrentGame = false;

    /**
     * GameController constructor which runs the game
     */
    public GameController() {
        initializeGame();

        // Keep running game until quitted
        while (!gameQuitted) {

            if (gameStarted) {
                createNewGame();
                runGame();
            }

            sleep(100);
        }
    }

    /**
     * Return the player whose movie it is currently
     * @return
     */
    private Player getCurrentPlayer() {
        switch(getCurrentMoveColor()) {
            case Constants.WHITE:
                return whitePlayer;
            case Constants.BLACK:
                return blackPlayer;
            default:
                return null;
        }
    }

    /**
     * Return the current move color
     * @return
     */
    public String getCurrentMoveColor() {
        switch(turn) {
            case 0:
                return Constants.WHITE;
            case 1:
                return Constants.BLACK;
            default:
                return "";
        }
    }

    /**
     * Initialize the game and the view and the players and the player names
     */
    private void initializeGame() {
        board = new Board(length, width);
        turn = 0;

        boardView = new BoardView(this);

        whitePlayer = new Player(Constants.WHITE, "P0");
        blackPlayer = new Player(Constants.BLACK, "P1");

        setPlayerNames();
    }

    /**
     * Add the custom pieces to the board
     */
    private void addCustomPieces() {
        Ferz whiteFerz1 = new Ferz(Constants.WHITE);
        Ferz whiteFerz2 = new Ferz(Constants.WHITE);
        Ferz blackFerz1 = new Ferz(Constants.BLACK);
        Ferz blackFerz2 = new Ferz(Constants.BLACK);

        Princess whitePrincess = new Princess(Constants.WHITE);
        Princess blackPrincess = new Princess(Constants.BLACK);

        board.setPiece(whiteFerz1, 6, 2);
        board.setPiece(whiteFerz2, 6, 5);
        board.setPiece(blackFerz1, 1, 2);
        board.setPiece(blackFerz2, 1, 5);
        board.setPiece(whitePrincess, 7, 3);
        board.setPiece(blackPrincess, 0, 3);
    }

    /**
     * Create a new game, initializing a new board and adding custom pieces if a custom game is set
     * Reset the player states and start the game
     */
    private void createNewGame() {

        board = new Board(length, width);

        if (gameCustom)
            addCustomPieces();

        resetGameState();
        boardView.drawChessBoard();

        whitePlayer.resetState();
        blackPlayer.resetState();

        turn = 0;
        boardView.printPlayerScore(whitePlayer, blackPlayer, turn);

        stopCurrentGame = false;
    }

    /**
     * Keep looping to run the game until the current game is stopped
     */
    public void runGame() {
        while (gameStarted && !stopCurrentGame) {
            // Loop
            sleep(100);
        }
    }

    /**
     * Reset the game states of forfeit, restart, and quit
     */
    public void resetGameState() {
        gameQuitted = false;
        gameCustom  = false;
    }

    /**
     * Reset the GUI state and highlights
     */
    public void resetCurrentMoveState() {
        boardView.clearHighlightedMoves();

        selectedPiece = null;
        startCoordinate = null;
    }

    /**
     * Switch whose turn it is, set the current move color, and update the UI correspondingly
     */
    private void switchTurns() {
        turn = 1 - turn;
        boardView.printPlayerScore(whitePlayer, blackPlayer, turn);

        resetCurrentMoveState();
    }

    /**
     * Action to take when a piece square is clicked
     * If no piece has already been selected, select this piece if a valid piece
     * @param x
     * @param y
     */
    public void pieceSquareClick(int x, int y) {
        if (gameStarted) {
            if (selectedPiece == null)
                setSelectedPieceIfValid(x, y);
            else {
                Coordinate destination = new Coordinate(x, y);
                if (selectedPiece.possibleMoves().contains(destination))
                    makeBoardMove(selectedPiece, destination);
                else
                    setSelectedPieceIfValid(x, y);
            }
        }
    }

    /**
     * Helper function to check if the selected piece is controlled by the player
     * If valid, reset the highlights and update the selectedPiece variable
     * Else, do nothing
     * @param x
     * @param y
     */
    private void setSelectedPieceIfValid(int x, int y) {
        Piece piece = Board.getPiece(x, y);
        if (Player.validatePiece(piece, getCurrentMoveColor(), boardView, selectedPiece)) {
            resetCurrentMoveState();

            selectedPiece = piece;
            startCoordinate = new Coordinate(x, y);
            boardView.highlightPieceAndMoves(piece, x, y);
        }
    }

    /**
     * Action to take when an empty square is clicked
     * If a piece has already been selected, check if the clicked square is in the set of possible moves for the piece.
     * If it is, try making the move to see if it puts the player in check.
     * If not, alert the player of the invalid move
     * @param x
     * @param y
     */
    public void emptySquareClick(int x, int y) {
        if (gameStarted) {
            if (selectedPiece != null) {
                Coordinate destination = new Coordinate(x, y);
                if (selectedPiece.possibleMoves().contains(destination))
                    makeBoardMove(selectedPiece, destination);
                else
                    boardView.printAlert("Invalid move!");
            }
        }
    }

    /**
     * Run the selected move to see if it puts the player in check
     * If it does, make the move on the GUI, switch turns, and check the model status for check/checkmate/stalemate
     * Else, undo the move and alert the player of the invalid move putting them in check.
     * @param piece
     * @param destination
     */
    private void makeBoardMove(Piece piece, Coordinate destination) {
        Coordinate start = piece.getCoordinate();
        Piece existing = Board.getPiece(destination);
        if (board.movePiece(piece, destination)) {
            boardView.makeMoveGUI(piece, start, destination);

            lastMove = new Move(piece, existing, start, destination);

            switchTurns();
            checkBoardStatus();
        }
        else
            boardView.printAlert("Invalid move! Player in check.");
    }

    /**
     * Check the current player's status for check, checkmate, and stalemate
     * If in checkmate, end the current game and set the other player as the winner
     * If in stalemate, end the current game and update the GUI
     */
    private void checkBoardStatus() {
        Player player = getCurrentPlayer();
        player.checkBoardStatus(boardView, board);

        if (player.checkMate) {
            stopCurrentGame = true;
            gameStarted = false;

            switchTurns();  // Switch turns back to winner

            winGame(getCurrentPlayer());
        }

        if (board.isInStalemate(player.color)) {
            stopCurrentGame = true;
            gameStarted = false;

            boardView.printAlert("Stalemate!");
        }
    }

    /**
     * Set the player as the winner on the GUI, increment the player's score, and update the score on the GUI
     * @param winner
     */
    private void winGame(Player winner) {
        boardView.winGame(winner);
        winner.score++;
        boardView.printPlayerScore(whitePlayer, blackPlayer, turn);
    }

    /**
     * Sleep a thread ms number of milliseconds
     * Catch and print out any exceptions
     * @param ms
     */
    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set player names as input from the GUI
     * If none or empty string is entered, set to default of P0 and P1
     */
    private void setPlayerNames() {
        String player1Name = boardView.setPlayerName("0");
        String player2Name = boardView.setPlayerName("1");

        if (player1Name != null && !player1Name.isEmpty())
            whitePlayer.name = player1Name;
        if (player2Name != null && !player2Name.isEmpty())
            blackPlayer.name = player2Name;
    }

    public void handleGameStart() {
        gameStarted = true;
    }

    /**
     * Undo the move if lastMove is not null (i.e. move has been made or move not already undone).
     * If undoing the move, switch turns back to the original player's move
     * Else, alert the player that an undo is not valid
     */
    public void handleGameUndo() {
        if (gameStarted) {
            if (lastMove != null) {
                boardView.undoMoveGUI(lastMove.piece, lastMove.existing, lastMove.start, lastMove.destination);
                board.undoMove(lastMove.piece, lastMove.existing, lastMove.start, lastMove.destination);
                lastMove = null;
                switchTurns();
            }
            else
                boardView.printAlert("Cannot undo move!");
        }
    }

    /**
     * If the restart button is pressed, pop up a confirmation
     * If no or window closed, ignore.
     * If yes, notify the players, stop the current game, and reset the GUI state
     */
    public void handleGameRestart() {
        if (gameStarted) {
            int response = boardView.getRestartConfirmation();
            switch (response) {
                case JOptionPane.NO_OPTION:
                case JOptionPane.CLOSED_OPTION:
                    break;
                case JOptionPane.YES_OPTION:
                    boardView.printAlert("Game is restarting!");
                    stopCurrentGame = true;
                    resetCurrentMoveState();
            }
        }
    }

    /**
     * If the game is forfeited, end the current game
     * Mark the other player as the winner of this game
     */
    public void handleGameForfeit() {
        if (gameStarted) {
            gameStarted = false;
            stopCurrentGame = true;

            switchTurns();
            winGame(getCurrentPlayer());
        }

    }

    /**
     * If the game is quit, exit the game.
     */
    public void handleGameQuit() {
        gameQuitted = true;
        gameStarted = false;
        stopCurrentGame = true;
        System.exit(0);
    }

    /**
     * Add the custom pieces to the model
     * Can only be used when the game has not started yet
     */
    public void handleGameCustom() {
        if (!stopCurrentGame) {
            gameCustom = true;
            addCustomPieces();
            boardView.drawChessBoard();
        }
        else
            boardView.printAlert("Custom pieces can only be added at beginning of game!");
    }

    /**
     * Create a new game
     * @param args
     */
    public static void main(String[] args) {
        GameController gameController = new GameController();
    }
}

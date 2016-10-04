package main;

import main.model.Board;
import main.model.Coordinate;
import main.pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by samir on 9/13/16.
 */
public class BoardView {

    private GameController gameController;

    // ===== Java Swing Vars =====
    private JFrame window;
    private JButton[][] boardGUI;
    private final int windowSize = 700;

    // ===== GameController Dimensions =====
    // Chess model dimensions
    private final int boardLength = Board.getLength();
    private final int boardWidth = Board.getWidth();
    // Overall GUI dimensions
    private final int GUILength = boardLength + 1;
    private final int GUIWidth = boardWidth + 1;

    // Possible moves the current move can make
    private ArrayList<Coordinate> highlightedMoves = new ArrayList<>();

    /**
     * ===================================
     *        Print/Alert Methods
     * ===================================
     */
    public void printAlert(String alert) {
        JOptionPane.showMessageDialog(null, alert);
    }

    /**
     * Prints the player score on the GUI, bolding the player whose turn it is
     * @param whitePlayer
     * @param blackPlayer
     * @param move
     */
    public void printPlayerScore(Player whitePlayer, Player blackPlayer, int move) {
        String white = "<html>" + whitePlayer.name + ": " + whitePlayer.score + "</html>";
        String black = "<html>" + blackPlayer.name + ": " + blackPlayer.score + "</html>";
        if (move == 0)
            white = "<html><b>" + whitePlayer.name + ": " + whitePlayer.score + "</b></html>";
        else
            black = "<html><b>" + blackPlayer.name + ": " + blackPlayer.score + "</b></html>";

        boardGUI[GUIWidth - 1][6].setText(white);
        boardGUI[GUIWidth - 1][7].setText(black);
    }

    /**
     * ===================================
     *       Update UI State Methods
     * ===================================
     */

    /**
     * Reset the square at this coordinate to its original background color
     * @param x
     * @param y
     */
    private void resetSquareBackground(int x, int y) {
        if ((x + y) % 2 == 0)
            boardGUI[x][y].setBackground(Color.white);
        else
            boardGUI[x][y].setBackground(Color.LIGHT_GRAY);
    }

    public void highlightPieceAndMoves(Piece piece, int x, int y) {
        highlightedMoves.clear();

        boardGUI[x][y].setBackground(new Color(255, 210, 232));

        ArrayList<Coordinate> moves = piece.possibleMoves();
        for (Coordinate coordinate : moves) {
            boardGUI[coordinate.x][coordinate.y].setBackground(new Color(255, 255, 204));
            highlightedMoves.add(coordinate);
        }
    }

    /**
     * Clear the highlighted squares
     */
    public void clearHighlightedMoves() {
        if (gameController.startCoordinate != null)
            resetSquareBackground(gameController.startCoordinate.x, gameController.startCoordinate.y);

        for (Coordinate coordinate : highlightedMoves) {
            resetSquareBackground(coordinate.x, coordinate.y);
        }

        highlightedMoves.clear();
    }

    /**
     * ===================================
     *         Draw UI Components
     * ===================================
     */

    /**
     * Gets the chess model from the backend and redraws the front end to update the positions of the pieces
     */
    public void drawChessBoard() {

        Piece[][] board = Board.getBoard();

        for (int x = 0; x < boardWidth; x++) {
            for (int y = 0; y < boardLength; y++) {
                resetSquareBackground(x, y);

                if (board[x][y] != null)
                    setPieceOnBoard(board[x][y], x, y);
                else
                    setEmptyOnBoard(x, y);
            }
        }
    }

    /**
     * Initialize all the panels of the JFrame window (each square is a panel)
     * Initialize a piece if a piece exists there on the model
     */
    private void initializePanels() {
        Piece[][] board = Board.getBoard();

        for (int x = 0; x < boardWidth; x++) {
            for (int y = 0; y < boardLength; y++) {
                boardGUI[x][y] = createButton();
                boardGUI[x][y].setLayout(new GridLayout());

                resetSquareBackground(x, y);

                if (board[x][y] != null)
                    setPieceOnBoard(board[x][y], x, y);
                else
                    setEmptyOnBoard(x, y);

                window.add(boardGUI[x][y]);
            }
        }

        // Add the buttons for starting, restarting, and forfeiting
        createActionButton("Start", GUIWidth - 1, 0);
        createActionButton("Undo", GUIWidth - 1, 1);
        createActionButton("Restart", GUIWidth - 1, 2);
        createActionButton("Forfeit", GUIWidth - 1, 3);
        createActionButton("Quit", GUIWidth - 1, 4);
        createActionButton("Custom", GUIWidth - 1, 5);

        // Create labels for player score
        boardGUI[GUIWidth - 1][6] = createButton();
        boardGUI[GUIWidth - 1][7] = createButton();
        window.add(boardGUI[GUIWidth - 1][6]);
        window.add(boardGUI[GUIWidth - 1][7]);
    }

    /**
     * Allow for custom player names at the beginning of the gameController
     * @param player
     * @return
     */
    public String setPlayerName(String player) {
        return JOptionPane.showInputDialog(null, "Player " + player + " name?", null);
    }

    /**
     * Create a JButton with a corresponding action at the following coordinates
     * Buttons for start, undo, restart, forfeit, quit, and custom gameController
     * @param label     Label of the button
     * @param x
     * @param y
     */
    private void createActionButton(String label, int x, int y) {
        boardGUI[x][y] = new JButton(label);
        boardGUI[x][y].setLayout(new GridLayout());

        boardGUI[x][y].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch(label) {
                    case "Start":
                        gameController.handleGameStart();
                        break;
                    case "Undo":
                        gameController.handleGameUndo();
                        //TODO: Implement undo
                        break;
                    case "Restart":
                        gameController.handleGameRestart();
                        break;
                    case "Forfeit":
                        gameController.handleGameForfeit();
                        break;
                    case "Quit":
                        gameController.handleGameQuit();
                        break;
                    case "Custom":
                        gameController.handleGameCustom();
                        break;
                }
            }
        });

        window.add(boardGUI[x][y]);
    }

    /**
     * Pop up the restart confirmation and return the response by the player
     * @return  Response by the player
     */
    public int getRestartConfirmation() {
        return JOptionPane.showConfirmDialog(null, "Does the other player want to restart?", "Restart?",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Create a new transparent button
     * @return  The created button
     */
    private JButton createButton() {
        JButton button = new JButton();

        button.setOpaque(true);
        button.setBorderPainted(false);

        return button;
    }

    /**
     * Set the button's icon to the image corresponding to the piece
     * @param button    Button whose icon to change
     * @param piece     Piece with an imagePath variable to the destination of the icon
     */
    private void setButtonImage(JButton button, Piece piece) {
        ImageIcon icon;
        java.net.URL imgURL = getClass().getResource(piece.imagePath);
        if (imgURL != null) {
            icon = new ImageIcon(imgURL, piece.getType());
        } else {
            System.err.println("Couldn't find file: " + piece.imagePath);
            icon = new ImageIcon();
        }

        // Scale the image to fit the grids
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(-1, windowSize/ boardWidth, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImg);

        // Set the icon in the button
        button.setIcon(icon);
    }

    /**
     * Adds a piece at the coordinate specified by (x, y)
     * Adds a piece action listener that corresponds to a square with a piece on it
     * @param piece
     * @param x
     * @param y
     */
    private void setPieceOnBoard(Piece piece, int x, int y) {
        JButton button = boardGUI[x][y];

        setButtonImage(button, piece);

        removeAllActionListeners(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.pieceSquareClick(x, y);
            }
        });
    }

    /**
     * Create an empty model button that has an ActionListener.
     * Empty buttons can only act as the destination for a selected piece.
     * If this button is a valid move for the selected piece, set the destinationCoordinate.
     * Else, tell the user it is an invalid move.
     * @param x
     * @param y
     */
    private void setEmptyOnBoard(int x, int y) {
        JButton button = boardGUI[x][y];
        button.setIcon(null);

        removeAllActionListeners(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.emptySquareClick(x, y);
            }
        });
    }

    /**
     * Remove all action listeners from the corresponding button
     * @param button
     */
    private void removeAllActionListeners(JButton button) {
        for (ActionListener al : button.getActionListeners()) {
            button.removeActionListener(al);
        }
    }

    /**
     * Make a move on the GUI, moving a piece to its new coordinate
     * @param piece
     * @param start
     * @param destination
     */
    public void makeMoveGUI(Piece piece, Coordinate start, Coordinate destination) {
        setEmptyOnBoard(start.x, start.y);
        setPieceOnBoard(piece, destination.x, destination.y);

        clearHighlightedMoves();
    }

    public void undoMoveGUI(Piece piece, Piece existing, Coordinate start, Coordinate destination) {
        makeMoveGUI(piece, destination, start);

        if (existing != null)
            setPieceOnBoard(existing, destination.x, destination.y);
    }

    /**
     * End the gameController by setting gameStarted to false. Redraw the model to show the last move.
     * Show a dialog that prints the winning player
     * @param winner
     */
    public void winGame(Player winner) {
        printAlert(winner.name + " is the winner of the game!");
    }

    /**
     * Create the GUI
     */
    public BoardView(GameController gameController) {
        this.gameController = gameController;

        window = new JFrame("Chess");
        window.setLayout(new GridLayout(GUILength, GUIWidth));
        boardGUI = new JButton[GUILength][GUIWidth];

        initializePanels();

        window.setSize(windowSize, windowSize);
        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }
}

package main;

import main.model.Board;
import main.pieces.Piece;

/**
 * Created by samir on 9/5/16.
 */
public class Player {
    public String color;
    public boolean check;
    public boolean checkMate;
    public int score;

    public String name;

    /**
     * Player constructor
     * @param color
     * @param name
     */
    public Player(String color, String name) {
        this.color = color;
        this.name = name;

        check = false;
        checkMate = false;
        score = 0;
    }

    /**
     * Validates that the piece at the given coordinate is a valid piece that the player controls
     * If not valid, alert the player with the corresponding message
     * @param piece
     * @param color
     * @param boardView
     * @param selectedPiece
     * @return  True if valid, False if not
     */
    public static boolean validatePiece(Piece piece, String color, BoardView boardView, Piece selectedPiece) {
        if (piece == null) {
            boardView.printAlert("No piece at coordinates.");
            return false;
        }
        else if (!piece.getColor().equals(color)) {
            if (selectedPiece != null)
                boardView.printAlert("Invalid move! Cannot capture this piece.");
            else
                boardView.printAlert("Piece at coordinates is other team's piece.");
            return false;
        }
        return true;
    }

    /**
     * Reset the check and checkmate state of this player
     */
   public void resetState() {
       check = false;
       checkMate = false;
   }

    /**
     * Checks the boardGUI after a move to see if the move placed this player in check or checkmate
     * @param boardView
     * @param board
     */
    public void checkBoardStatus(BoardView boardView, Board board) {
        check = board.isInCheck(color);

        if (check) {
            checkMate = board.isInCheckMate(color);
            if (checkMate)
                boardView.printAlert(name + " is in checkmate!");
            else
                boardView.printAlert(name + " is in check!");
        }
    }
}

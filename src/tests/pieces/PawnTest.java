package tests.pieces;

import main.*;
import main.model.Board;
import main.model.Coordinate;
import main.pieces.Pawn;
import main.pieces.Piece;
import tests.Common;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by samir on 9/3/16.
 */
public class PawnTest {
    /**
     * Valid constructor for a pawn should not throw an exception
     */
    @Test
    public void validPawnConstructor() throws Exception {
        String color = Constants.WHITE;
        int xCoordinate = 1;
        int yCoordinate = 1;
        Piece pawn = new Pawn(xCoordinate, yCoordinate, color);

        assertEquals(color, pawn.getColor());
        assertEquals(xCoordinate, pawn.getCoordinate().x);
        assertEquals(yCoordinate, pawn.getCoordinate().y);
    }

    /**
     * Test for valid pawn movement (should be able to move)
     */
    @Test
    public void validPawnMovement() throws Exception {
        Board board = new Board(8, 8);
        Piece pawn = Board.getPiece(new Coordinate(1, 1));

        // Pawn should be able to move either one or two spaces
        ArrayList<Coordinate> possibleMovesList = new ArrayList<Coordinate>();
        possibleMovesList.add(new Coordinate(2, 1));
        possibleMovesList.add(new Coordinate(3, 1));

        Common.assertEqualMoves(possibleMovesList, pawn.possibleMoves(), Constants.PAWN);
    }

    /**c
     * Test for invalid pawn movement if they have no available moves
     */
    @Test
    public void invalidPawnMovement() throws Exception {
        Board board = new Board(8, 8);

        // Block off pawn at (1,0)
        board.setPiece(new Pawn(2, 0, Constants.BLACK), 2, 0);

        // Uncomment to see scenario on boardGUI
        // boardGUI.printBoard();

        Piece pawn = Board.getPiece(1, 0);
        ArrayList<Coordinate> possibleMovesList = new ArrayList<Coordinate>();

        Common.assertEqualMoves(possibleMovesList, pawn.possibleMoves(), Constants.PAWN);
    }
}

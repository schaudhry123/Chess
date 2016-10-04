package tests.pieces;

import main.*;
import main.pieces.Piece;
import main.model.Board;
import main.model.Coordinate;
import main.pieces.Rook;
import tests.Common;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by samir on 9/5/16.
 */
public class RookTest {
    /**
     * Valid constructor for a rook should not throw an exception
     */
    @Test
    public void validRookConstructor() throws Exception {
        String color = Constants.WHITE;
        int x = 1;
        int y = 1;
        Piece rook = new Rook(color);
        rook.setCoordinate(new Coordinate(x, y));

        assertEquals(color, rook.getColor());
        assertEquals(x, rook.getCoordinate().x);
        assertEquals(y, rook.getCoordinate().y);
    }

    /**
     * Test for valid rook movement (should be able to move)
     * Check the valid moves match up
     * @throws Exception
     */
    @Test
    public void validRookMovement() throws Exception {
        Board board = new Board(8, 8);
        board.setPiece(new Rook(Constants.BLACK), 3, 2);

        Piece rook = board.getPiece(3, 2);

        // Rook should be able to move to all available spaces (or space occupied by enemy) in a line
        ArrayList<Coordinate> possibleMovesList = new ArrayList<Coordinate>();
        possibleMovesList.add(new Coordinate(2, 2));
        possibleMovesList.add(new Coordinate(4, 2));
        possibleMovesList.add(new Coordinate(5, 2));
        possibleMovesList.add(new Coordinate(6, 2));

        possibleMovesList.add(new Coordinate(3, 0));
        possibleMovesList.add(new Coordinate(3, 1));
        possibleMovesList.add(new Coordinate(3, 3));
        possibleMovesList.add(new Coordinate(3, 4));
        possibleMovesList.add(new Coordinate(3, 5));
        possibleMovesList.add(new Coordinate(3, 6));
        possibleMovesList.add(new Coordinate(3, 7));


        // Uncomment to see scenario on boardGUI
        //boardGUI.printBoard();

        Common.assertEqualMoves(possibleMovesList, rook.possibleMoves(), "Rook");
    }
}

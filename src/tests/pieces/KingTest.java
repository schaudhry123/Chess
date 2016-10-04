package tests.pieces;

import main.*;
import main.model.Board;
import main.model.Coordinate;
import main.pieces.King;
import main.pieces.Piece;
import tests.Common;

import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * Created by samir on 9/5/16.
 */
public class KingTest {
    /**
     * Valid constructor for a king should not throw an exception
     */
    @Test
    public void validQueenConstructor() throws Exception {
        String color = Constants.WHITE;
        int x = 1;
        int y = 1;
        Piece king = new King(color);
        king.setCoordinate(new Coordinate(x, y));

        assertEquals(color, king.getColor());
        assertEquals(x, king.getCoordinate().x);
        assertEquals(y, king.getCoordinate().y);
    }

    /**
     * Test for valid queen movement (should be able to move)
     * Check the valid moves match up
     * @throws Exception
     */
    @Test
    public void validKingMovement() throws Exception {
        Board board = new Board(8, 8);
        board.setPiece(new King(Constants.BLACK), 3, 2);

        Piece king = Board.getPiece(3, 2);

        ArrayList<Coordinate> possibleMovesList = new ArrayList<Coordinate>();
        // King can move to all adjacent squares
        possibleMovesList.add(new Coordinate(3, 1));
        possibleMovesList.add(new Coordinate(3, 3));
        possibleMovesList.add(new Coordinate(4, 2));
        possibleMovesList.add(new Coordinate(2, 2));
        possibleMovesList.add(new Coordinate(4, 3));
        possibleMovesList.add(new Coordinate(2, 1));
        possibleMovesList.add(new Coordinate(4, 1));
        possibleMovesList.add(new Coordinate(2, 3));

        Common.assertEqualMoves(possibleMovesList, king.possibleMoves(), Constants.KING);
    }
}

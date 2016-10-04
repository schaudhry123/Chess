package tests.pieces;

import main.*;
import main.model.Board;
import main.model.Coordinate;
import main.pieces.Bishop;
import main.pieces.Piece;
import tests.Common;

import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by samir on 9/4/16.
 */
public class BishopTest {
    /**
     * Valid constructor for a bishop should not throw an exception
     */
    @Test
    public void validBishopConstructor() throws Exception {
        String color = Constants.WHITE;
        int x = 1;
        int y = 1;
        Piece bishop = new Bishop(color);
        bishop.setCoordinate(new Coordinate(x, y));

        assertEquals(color, bishop.getColor());
        assertEquals(x, bishop.getCoordinate().x);
        assertEquals(y, bishop.getCoordinate().y);
    }

    /**
     * Test for valid bishop movement (should be able to move)
     * Check the valid moves match up
     * @throws Exception
     */
    @Test
    public void validBishopMovement() throws Exception {
        Board board = new Board(8, 8);
        board.setPiece(new Bishop(Constants.BLACK), 3, 2);

        Piece bishop = Board.getPiece(3, 2);

        // Bishop should be able to move in diagonals
        ArrayList<Coordinate> possibleMovesList = new ArrayList<Coordinate>();
        possibleMovesList.add(new Coordinate(2, 1));
        possibleMovesList.add(new Coordinate(2, 3));
        possibleMovesList.add(new Coordinate(4, 1));
        possibleMovesList.add(new Coordinate(4, 3));
        possibleMovesList.add(new Coordinate(5, 0));
        possibleMovesList.add(new Coordinate(5, 4));
        possibleMovesList.add(new Coordinate(6, 5));

        Common.assertEqualMoves(possibleMovesList, bishop.possibleMoves(), Constants.BISHOP);
    }

    /**
     * Test for invalid bishop movement (no available moves at start)
     */
    @Test
    public void invalidBishopMovement() throws Exception {
        Board board = new Board(8, 8);

        Piece bishop = Board.getPiece(0, 2);

        ArrayList<Coordinate> possibleMovesList = new ArrayList<Coordinate>();
        Common.assertEqualMoves(possibleMovesList, bishop.possibleMoves(), Constants.BISHOP);
    }
}

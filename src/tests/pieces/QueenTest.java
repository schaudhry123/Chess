package tests.pieces;

import main.*;
import main.pieces.Piece;
import main.model.Board;
import main.model.Coordinate;
import main.pieces.Queen;
import tests.Common;

import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * Created by samir on 9/3/16.
 */
public class QueenTest {

    /**
     * Valid constructor for a queen should not throw an exception
     */
    @Test
    public void validQueenConstructor() throws Exception {
        String color = Constants.WHITE;
        int x = 1;
        int y = 1;
        Piece queen = new Queen(color);
        queen.setCoordinate(new Coordinate(x, y));

        assertEquals(color, queen.getColor());
        assertEquals(x, queen.getCoordinate().x);
        assertEquals(y, queen.getCoordinate().y);
    }

    /**
     * Test for valid queen movement (should be able to move)
     * Check the valid moves match up
     *
     * @throws Exception
     */
    @Test
    public void validQueenMovement() throws Exception {
        Board board = new Board(8, 8);
        board.setPiece(new Queen(Constants.BLACK), 3, 2);

        Piece queen = Board.getPiece(3, 2);

        ArrayList<Coordinate> possibleMovesList = new ArrayList<Coordinate>();
        // Queen should be able to move in diagonals
        possibleMovesList.add(new Coordinate(2, 1));
        possibleMovesList.add(new Coordinate(2, 3));
        possibleMovesList.add(new Coordinate(4, 1));
        possibleMovesList.add(new Coordinate(4, 3));
        possibleMovesList.add(new Coordinate(5, 0));
        possibleMovesList.add(new Coordinate(5, 4));
        // Queen should also be able to move in lines
        possibleMovesList.add(new Coordinate(2, 2));
        possibleMovesList.add(new Coordinate(3, 1));
        possibleMovesList.add(new Coordinate(3, 0));
        possibleMovesList.add(new Coordinate(3, 3));
        possibleMovesList.add(new Coordinate(3, 4));
        possibleMovesList.add(new Coordinate(3, 5));
        possibleMovesList.add(new Coordinate(3, 6));
        possibleMovesList.add(new Coordinate(3, 7));
        possibleMovesList.add(new Coordinate(4, 2));
        possibleMovesList.add(new Coordinate(5, 2));
        possibleMovesList.add(new Coordinate(6, 2));
        possibleMovesList.add(new Coordinate(6, 5));

        // Uncomment to see scenario on boardGUI
        // boardGUI.printBoard();

        Common.assertEqualMoves(possibleMovesList, queen.possibleMoves(), Constants.QUEEN);
    }

    /**
     * Test for invalid queen movement (i.e. no possible moves)
     * Movement off the boardGUI should be rejected
     *
     * @throws Exception
     */
    @Test
    public void invalidQueenMovement() throws Exception {
        Board board = new Board(8, 8);

        Piece queen = Board.getPiece(0, 3);
        ArrayList<Coordinate> possibleMovesList = new ArrayList<Coordinate>();

        Common.assertEqualMoves(possibleMovesList, queen.possibleMoves(), Constants.QUEEN);

        // Try to move a queen off the boardGUI
        Piece newQueen = new Queen(Constants.WHITE);
        board.setPiece(newQueen, 3, 2);

        // Uncomment to see scenario on boardGUI
        // boardGUI.printBoard();

        assertFalse("Queen should not move off the boardGUI", board.movePiece(newQueen, new Coordinate(3, 8)));
    }
}

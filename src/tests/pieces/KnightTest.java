package tests.pieces;

import main.*;
import main.model.Board;
import main.model.Coordinate;
import main.pieces.Knight;
import main.pieces.Piece;
import tests.Common;

import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * Created by samir on 9/5/16.
 */
public class KnightTest {
    /**
     * Valid constructor for a knight should not throw an exception
     */
    @Test
    public void validKnightConstructor() throws Exception {
        String color = Constants.WHITE;
        int x = 1;
        int y = 1;
        Piece knight = new Knight(color);
        knight.setCoordinate(new Coordinate(x, y));

        assertEquals(color, knight.getColor());
        assertEquals(x, knight.getCoordinate().x);
        assertEquals(y, knight.getCoordinate().y);
    }

    /**
     * Test for valid knight movement (should be able to move)
     * Check the valid moves match up
     * @throws Exception
     */
    @Test
    public void validKnightMovement() throws Exception {
        Board board = new Board(8, 8);

        Piece knight = new Knight(Constants.BLACK);
        board.setPiece(knight, 4, 2);

        ArrayList<Coordinate> possibleMovesList = new ArrayList<Coordinate>();

        possibleMovesList.add(new Coordinate(3, 0));
        possibleMovesList.add(new Coordinate(3, 4));
        possibleMovesList.add(new Coordinate(5, 0));
        possibleMovesList.add(new Coordinate(5, 4));
        possibleMovesList.add(new Coordinate(2, 1));
        possibleMovesList.add(new Coordinate(2, 3));
        possibleMovesList.add(new Coordinate(6, 1));
        possibleMovesList.add(new Coordinate(6, 3));

        // Uncomment to see scenario on boardGUI
        // boardGUI.printBoard();

        Common.assertEqualMoves(possibleMovesList, knight.possibleMoves(), Constants.KNIGHT);
    }

    /**
     * Test for invalid knight movement
     * Moving off the boardGUI should be rejected
     */
    @Test
    public void invalidKnightMovement() throws Exception {
        Board board = new Board(8, 8);

        Piece knight = Board.getPiece(0, 1);

        // Try to move knight off the boardGUI
        assertFalse("Knight was able to move off the boardGUI", board.movePiece(knight, new Coordinate(-1, 3)));
        ArrayList<Coordinate> possibleMovesList = new ArrayList<Coordinate>();

        possibleMovesList.add(new Coordinate(2, 0));
        possibleMovesList.add(new Coordinate(2, 2));

        // Uncomment to see scenario on boardGUI
        // boardGUI.printBoard();

        Common.assertEqualMoves(possibleMovesList, knight.possibleMoves(), Constants.KNIGHT);
    }


}

package tests.pieces;

import main.*;
import main.model.Board;
import main.model.Coordinate;
import main.pieces.*;

import org.junit.Test;
import tests.Common;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by samir on 9/13/16.
 */
public class PrincessTest {

    @Test
    public void validPrincessConstructor() throws Exception {
        String color = Constants.WHITE;
        int x = 1;
        int y = 1;
        Piece princess = new Princess(color);
        princess.setCoordinate(new Coordinate(x, y));

        assertEquals(color, princess.getColor());
        assertEquals(x, princess.getCoordinate().x);
        assertEquals(y, princess.getCoordinate().y);
    }

    @Test
    public void validPrincessMovement() throws Exception {
        Board board = new Board(8, 8);

        Piece princess = new Princess(Constants.BLACK);
        board.setPiece(princess, 3, 2);

        ArrayList<Coordinate> possibleMovesList = new ArrayList<Coordinate>();
        // Queen should be able to move in diagonals
        possibleMovesList.add(new Coordinate(2, 1));
        possibleMovesList.add(new Coordinate(2, 3));
        possibleMovesList.add(new Coordinate(4, 1));
        possibleMovesList.add(new Coordinate(4, 3));
        possibleMovesList.add(new Coordinate(5, 0));
        possibleMovesList.add(new Coordinate(5, 4));
        possibleMovesList.add(new Coordinate(6, 5));
        // Queen should also be able to move like a knight
        possibleMovesList.add(new Coordinate(5, 1));
        possibleMovesList.add(new Coordinate(5, 3));
        possibleMovesList.add(new Coordinate(2, 4));
        possibleMovesList.add(new Coordinate(2, 0));
        possibleMovesList.add(new Coordinate(4, 0));
        possibleMovesList.add(new Coordinate(4, 4));

        // Uncomment to see scenario on boardGUI
        board.printBoard();

        Common.assertEqualMoves(possibleMovesList, princess.possibleMoves(), Constants.PRINCESS);
    }
}

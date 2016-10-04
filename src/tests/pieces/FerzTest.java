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
public class FerzTest {

    @Test
    public void validFerzConstructor() throws Exception {
        String color = Constants.WHITE;
        int x = 1;
        int y = 1;
        Piece ferz = new Ferz(color);
        ferz.setCoordinate(new Coordinate(x, y));

        assertEquals(color, ferz.getColor());
        assertEquals(x, ferz.getCoordinate().x);
        assertEquals(y, ferz.getCoordinate().y);
    }

    @Test
    public void validFerzMovement() throws Exception {
        Board board = new Board(8, 8);

        // Uncomment to see scenario on boardGUI
        // boardGUI.printBoard();

        Piece ferz = new Ferz(Constants.WHITE);
        board.setPiece(ferz, 2, 2);

        // Pawn should be able to move either one or two spaces
        ArrayList<Coordinate> possibleMovesList = new ArrayList<Coordinate>();
        possibleMovesList.add(new Coordinate(1, 1));
        possibleMovesList.add(new Coordinate(1, 3));
        possibleMovesList.add(new Coordinate(3, 1));
        possibleMovesList.add(new Coordinate(3, 3));

        Common.assertEqualMoves(possibleMovesList, ferz.possibleMoves(), Constants.FERZ);
    }

    @Test
    public void invalidFerzMovement() throws Exception {
        Board board = new Board(8, 8);

        // Uncomment to see scenario on boardGUI
        // boardGUI.printBoard();

        Piece ferz = new Ferz(Constants.BLACK);
        board.setPiece(ferz, 2, 2);

        // Pawn should be able to move either one or two spaces
        ArrayList<Coordinate> possibleMovesList = new ArrayList<Coordinate>();
        possibleMovesList.add(new Coordinate(3, 1));
        possibleMovesList.add(new Coordinate(3, 3));

        Common.assertEqualMoves(possibleMovesList, ferz.possibleMoves(), Constants.FERZ);
    }
}

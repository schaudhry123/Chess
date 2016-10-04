package tests;

import main.pieces.Piece;
import main.model.Board;
import main.model.Coordinate;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by samir on 9/5/16.
 */
public class Common {

    /**
     * Converts a Coordinate ArrayList to an Array and sorts it
     * @param movesList
     * @return
     */
    public static Coordinate[] convertListToArray(ArrayList<Coordinate> movesList) {
        Coordinate[] moves = new Coordinate[movesList.size()];

        movesList.toArray(moves);
        Arrays.sort(moves);

        return moves;
    }

    /**
     *
     */
    public static void assertEqualMoves(ArrayList<Coordinate> possibleMovesList, ArrayList<Coordinate> movesList, String piece) {
        Coordinate[] possibleMoves = convertListToArray(possibleMovesList);
        Coordinate[] moves = convertListToArray(movesList);

        assertArrayEquals(piece + " should have only have 2 possible moves", possibleMoves, moves);
    }

    /**
     * Set up a basic check scenario putting the black player in check
     */
    public static void setUpBasicCheckScenario(Board board) {
        Piece whitePawn = board.getPiece(6, 4);
        Piece blackPawn = board.getPiece(1, 5);
        Piece whiteQueen = board.getPiece(7, 3);

        board.movePiece(whitePawn, new Coordinate(4, 4));
        board.movePiece(blackPawn, new Coordinate(2, 5));
        board.movePiece(whiteQueen, new Coordinate(3, 7));
    }
}

package tests.pieces;

import main.*;

import main.model.Board;
import main.model.Coordinate;
import main.pieces.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by samir on 9/3/16.
 */
public class PieceTest {

    @Test
    public void captureAPiece() throws Exception {
        Board board = new Board(8, 8);

        board.setPiece(new Pawn(5, 2, Constants.BLACK), 5, 2);

        Piece whitePawn = Board.getPiece(6, 1);
        assertTrue("Pawn should capture enemy pawn", board.movePiece(whitePawn, new Coordinate(5, 2)));
    }

}
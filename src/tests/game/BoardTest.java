package tests.game;

import main.*;

import main.model.Board;
import main.model.Coordinate;
import main.pieces.*;
import org.junit.Test;

import tests.Common;

import static org.junit.Assert.*;

/**
 * Created by samir on 9/5/16.
 */
public class BoardTest {
    /**
     * Ensure correct initialization of chess boardGUI (order/placement of pieces)
     */
    @Test
    public void correctInitialization() throws Exception {
        Board board = new Board(8, 8);

        // Assert second row for both teams is all pawns
        for (int i = 0; i < 8; i++) {
            Piece blackPiece = board.getPiece(1, i);
            Piece whitePiece = board.getPiece(6, i);
            assertTrue(blackPiece instanceof Pawn);
            assertTrue(whitePiece instanceof Pawn);
        }

        // Assert correct initialization for first row of both teams
        for (int i = 0; i < 8; i++) {
            Piece blackPiece = board.getPiece(0, i);
            Piece whitePiece = board.getPiece(7, i);

            // Assert type of piece depending on index of column in boardGUI
            // 0, 7 = rook; 1, 6 = knight; 2, 5 = bishop; 3 = queen; 4 = king
            switch(i) {
                case 0:
                case 7:
                    assertTrue(blackPiece instanceof Rook);
                    assertTrue(whitePiece instanceof Rook);
                    break;

                case 1:
                case 6:
                    assertTrue(blackPiece instanceof Knight);
                    assertTrue(whitePiece instanceof Knight);
                    break;

                case 2:
                case 5:
                    assertTrue(blackPiece instanceof Bishop);
                    assertTrue(whitePiece instanceof Bishop);
                    break;

                case 3:
                    assertTrue(blackPiece instanceof Queen);
                    assertTrue(whitePiece instanceof Queen);
                    break;

                case 4:
                    assertTrue(blackPiece instanceof King);
                    assertTrue(whitePiece instanceof King);
                    break;
            }
        }
    }

    /**
     * Assert that valid moves are allowed and invalid moves are blocked
     * @throws Exception
     */
    @Test
    public void validAndInvalidMoves() throws Exception {
        Board board = new Board(8, 8);

        Piece whitePawn = board.getPiece(6, 4);
        assertTrue(board.movePiece(whitePawn, new Coordinate(4, 4)));

        Piece whiteQueen = board.getPiece(7, 3);
        assertTrue(board.movePiece(whiteQueen, new Coordinate(3, 7)));

        Piece blackPawn = board.getPiece(1, 4);
        assertTrue(board.movePiece(blackPawn, new Coordinate(3, 4)));
        assertFalse(board.movePiece(blackPawn, new Coordinate(4, 4)));

        Piece blackPawn2 = board.getPiece(1, 7);
        assertFalse(board.movePiece(blackPawn2, new Coordinate(3, 7)));
        assertTrue(board.movePiece(blackPawn2, new Coordinate(2, 7)));

        Piece whiteBishop = board.getPiece(7, 5);
        assertTrue(board.movePiece(whiteBishop, new Coordinate(3, 1)));
    }

    /**
     * Put black player in check, assert check method returns true for black player
     * and false for white player
     */
    @Test
    public void basicCheckTest() throws Exception {
        Board board = new Board(8, 8);

        Common.setUpBasicCheckScenario(board);

        // Uncomment to see scenario on boardGUI
        // boardGUI.printBoard();

        assertTrue(board.isInCheck(Constants.BLACK));
        assertFalse(board.isInCheck(Constants.WHITE));
    }

    /**
     * Quick basic check mate test
     */
    @Test
    public void basicCheckMateTest() throws Exception {
        Board board = new Board(8, 8);

        Piece blackPawn1 = board.getPiece(1, 4);
        Piece blackQueen = board.getPiece(0, 3);
        Piece whitePawn1 = board.getPiece(6, 5);
        Piece whitePawn2 = board.getPiece(6, 6);

        board.movePiece(blackPawn1, new Coordinate(3, 4));
        board.movePiece(whitePawn1, new Coordinate(4, 5));
        board.movePiece(whitePawn2, new Coordinate(4, 6));
        board.movePiece(blackQueen, new Coordinate(4, 7));

        // Uncomment to see scenario on boardGUI
        //boardGUI.printBoard();

        assertTrue(board.isInCheck(Constants.WHITE));
        assertTrue(board.isInCheckMate(Constants.WHITE));
    }

    /**
     * Check that can only be broken by moving other piece in front of king
     */
    @Test
    public void stopCheckWithOtherPiece() throws Exception {
        Board board = new Board(8, 8);

        Common.setUpBasicCheckScenario(board);

        // Uncomment to see scenario on boardGUI
        // boardGUI.printBoard();

        assertTrue(board.isInCheck(Constants.BLACK));
        assertFalse(board.isInCheckMate(Constants.BLACK));

        // Black player makes a valid move that blocks check, no longer in check
        Piece blackPawn = Board.getPiece(1, 6);
        assertTrue(board.movePiece(blackPawn, new Coordinate(2, 6)));
        assertFalse(board.isInCheck(Constants.BLACK));
    }

    /**
     * When the boardGUI is in stalemate, ensure that it is recognized
     */
    @Test
    public void testStalemateScenario() throws Exception {
        Board board = new Board(8, 8, "stalemate");

        // Uncomment to see scenario on boardGUI
        //boardGUI.printBoard();

        assertTrue("Failed to detect stalemate", board.isInStalemate(Constants.BLACK));
    }

    /**
     * When the boardGUI is not in stalemate, ensure it is not recognized
     */
    @Test
    public void testFalseStalemateScenario() throws Exception {
        Board board = new Board(8, 8);

        assertFalse("Incorrectly detected a stalemate", board.isInStalemate(Constants.BLACK));
        assertFalse("Incorrectly detected a stalemate", board.isInStalemate(Constants.WHITE));
    }

    /**
     * Ensure setPiece actually sets a piece on the boardGUI
     */
    @Test
    public void setPiecesOnBoard() throws Exception {
        Board board = new Board(8, 8);

        board.setPiece(new Pawn(3, 2, Constants.WHITE), 3, 2);

        Piece pawn = Board.getPiece(3, 2);

        assertTrue(pawn != null);
        assertTrue(pawn instanceof Pawn);
    }

    /**
     * Reject and undo an invalid move when a player is in check
     */
    @Test
    public void undoInvalidMove() throws Exception {
        Board board = new Board(8, 8);

        Common.setUpBasicCheckScenario(board);

        assertTrue(board.isInCheck(Constants.BLACK));

        // Let black player make a move that does not stop check
        // Move should be rejected
        Piece randomBlackPawn = Board.getPiece(1, 0);
        String assert_msg = "Move that does not take player out of check should be rejected";
        assertFalse(assert_msg, board.movePiece(randomBlackPawn, new Coordinate(3, 0)));

        assertTrue(board.isInCheck(Constants.BLACK));
    }

    @Test
    public void undoMove() throws Exception {
        Board board = new Board(8, 8);

        Coordinate start = new Coordinate(1, 1);
        Piece pawn = Board.getPiece(start);
        Coordinate destination = new Coordinate(3, 1);
        Piece existing = Board.getPiece(destination);

        assertTrue(board.movePiece(pawn, destination));
        assertTrue(Board.getPiece(start) == null);

        board.undoMove(pawn, existing, start, destination);

        assertTrue(Board.getPiece(start) == pawn);
        assertTrue(Board.getPiece(destination) == null);
    }
}

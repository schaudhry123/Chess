package main.model;

import main.Constants;
import main.pieces.*;

import java.util.ArrayList;

/**
 * Created by Samir on 9/1/16.
 */
public class Board {
    private static int length, width;

    private static Piece[][] board;                 // 2D array of pieces to represent the boardGUI
    private static ArrayList<Piece> blackPieces;
    private static ArrayList<Piece> whitePieces;

    private Piece blackKing;
    private Piece whiteKing;

    /**
     * Creates a boardGUI with the dimensions of boardLength and boardWidth
     */
    public Board(int length, int width) {
        Board.length = length;
        Board.width = width;

        board = new Piece[length][width];
        blackPieces = new ArrayList<>();
        whitePieces = new ArrayList<>();

        Initializer.initializeBoard(this);
    }

    /**
     * Custom constructor (for testing) to construct a stalemate scenario
     */
    public Board(int length, int width, String stalemate) {
        Board.length = length;
        Board.width = width;

        board = new Piece[length][width];
        blackPieces = new ArrayList<>();
        whitePieces = new ArrayList<>();

        initializeStalemateScenario();
    }

    // Return the boardLength of the boardGUI
    public static int getLength() {
        return length;
    }

    // Return the boardWidth of the boardGUI
    public static int getWidth() {
        return width;
    }

    /**
     * Return the piece at the location given by x and y
     */
    public static Piece getPiece(int x, int y) {
        return board[x][y];
    }

    /**
     * Return the piece at the corresponding coordinate
     */
    public static Piece getPiece(Coordinate coordinate) {
        return getPiece(coordinate.x, coordinate.y);
    }

    /**
     *
     * @param color
     * @return
     */
    public Piece getKing(String color) {
        if (color.equals(Constants.WHITE))
            return whiteKing;
        else
            return blackKing;
    }

    /**
     * Returns the opposing color to the passed in color
     * @param color     Only "B" or "W"
     */
    private String getOpposingColor(String color) {
        if (color.equals(Constants.WHITE))
            return Constants.BLACK;
        else
            return Constants.WHITE;
    }

    /**
     *
     * @param color
     * @return
     */
    private ArrayList<Piece> getListOfPieces(String color) {
        if (color.equals(Constants.WHITE))
            return whitePieces;
        else
            return blackPieces;
    }

    /**
     * Sets a piece at the location on the boardGUI and the map
     */
    public void setPiece(Piece piece, Coordinate coordinate) {
        setBoardPiece(piece, coordinate);
        if (piece.getColor().equals(Constants.WHITE))
            whitePieces.add(piece);
        else
            blackPieces.add(piece);
        piece.setCoordinate(coordinate);
    }

    /**
     * Sets a piece at the x and y coordinates on the boardGUI and map
     * Helper function for initialization of pieces on boardGUI
     */
    public void setPiece(Piece piece, int x, int y) {
        setPiece(piece, new Coordinate(x, y));
    }

    /**
     * Remove a piece from the map and boardGUI
     */
    public void removePiece(Piece piece) {
        if (piece != null) {
            Coordinate coordinate = removePieceFromLists(piece);
            if (coordinate != null)
                removeBoardPiece(coordinate);
            piece.removePiece();
        }
    }

    /**
     * Remove a piece from the list of pieces and return its coordinates
     */
    private static Coordinate removePieceFromLists(Piece piece) {
        if (piece.getColor().equals(Constants.WHITE))
            whitePieces.remove(piece);
        else
            blackPieces.remove(piece);
        return piece.getCoordinate();
    }

    /**
     * Move a piece from its coordinate to a new coordinate if a valid move.
     * If the player is in check after the move, reverts the move and fails.
     * Else, makes the moves.
     * @return True/false if the move succeeds or fails
     */
    public boolean movePiece(Piece piece, Coordinate coordinate) {
        if (piece.possibleMoves().contains(coordinate)) {

            if (canMoveWithoutCheck(piece, coordinate)) {
                movePieceToCoordinate(piece, coordinate);
                return true;
            }
            else {
                System.err.println("Invalid move: Player in check. Please re-enter coordinates.");
                return false;
            }
        }

        System.err.println("Invalid move. Please re-enter coordinates.");

        return false;
    }

    /**
     * Helper function to move a piece
     * Removes any piece already at the coordinate and move this piece there
     */
    public Piece movePieceToCoordinate(Piece piece, Coordinate coordinate) {
        Piece existing = getPiece(coordinate);
        if (existing != null)
            removePiece(existing);
        removePiece(piece);
        setPiece(piece, coordinate);
        return existing;
    }

    /**
     * Checks the boardGUI status for check
     * Iterates through every piece of the opposing team to see if any pieces can attack the king
     * @param color     Color of the player to look at for check
     */
    public boolean isInCheck(String color) {
        Piece king = getKing(color);
        Coordinate kingCoordinate = king.getCoordinate();

        String opposingColor = getOpposingColor(color);
        ArrayList<Piece> opposingPieces = getListOfPieces(opposingColor);

        // If any of the other player's pieces can move to this king, this player is in check
        for (Piece piece : opposingPieces) {
            ArrayList<Coordinate> moves = piece.possibleMoves();
            if (moves.contains(kingCoordinate)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Undo a move that was previously made
     * @param piece         piece that made the move
     * @param existing      piece that existed at the destination coordinate (null if none there)
     * @param start         coordinate that piece started at
     * @param destination   coordinate that piece moves to
     */
    public void undoMove(Piece piece, Piece existing, Coordinate start, Coordinate destination) {
        movePieceToCoordinate(piece, start);
        if (existing != null)
            movePieceToCoordinate(existing, destination);
    }

    /**
     * Helper function to determine if player can make a move without being in check
     * @param piece
     * @param coordinate
     * @return  True if they can, false otherwise
     */
    private boolean canMoveWithoutCheck(Piece piece, Coordinate coordinate) {
        Coordinate start = piece.getCoordinate();
        Piece existing = movePieceToCoordinate(piece, coordinate);
        if (isInCheck(piece.getColor())) {
            undoMove(piece, existing, start, coordinate);
            return false;
        }

        undoMove(piece, existing, start, coordinate);
        return true;
    }

    /**
     * Check if the player of this color is in checkmate
     * Look at all of your piece's moves, if any of them break check then return false
     * Else if no move removes check, return true
     * @param color
     * @return  True/False if in checkmate
     */
    public boolean isInCheckMate(String color) {
        // Check every piece's legal moves and if they stop check
        ArrayList<Piece> pieceList = new ArrayList<>(getListOfPieces(color));
        for (Piece piece : pieceList) {
            ArrayList<Coordinate> moves = piece.possibleMoves();

            for (Coordinate coordinate : moves) {
                if (canMoveWithoutCheck(piece, coordinate))
                    return false;
            }
        }

        return true;
    }

    /**
     * Function to check if a player has any valid moves that they can make
     * For all of their pieces, check if any piece can make a move without being in check
     * @param color
     * @return  True if they do, false if they don't
     */
    public boolean hasAnyMoves(String color) {
        ArrayList<Piece> pieceList = new ArrayList<>(getListOfPieces(color));
        for (Piece piece : pieceList) {
            ArrayList<Coordinate> moves = piece.possibleMoves();
            for (Coordinate coordinate : moves) {
                if (canMoveWithoutCheck(piece, coordinate))
                    return true;
            }
        }
        return false;
    }

    /**
     * Check if the boardGUI is in stalemate
     * If the player is not in check but has no legal moves.
     */
    public boolean isInStalemate(String color) {
        if (!isInCheck(color)) {
            if (!hasAnyMoves(color))
                return true;
        }
        return false;
    }

    /**
     * ####################################
     *         Board Update Methods
     * ####################################
     */

    /**
     * Returns the boardGUI and piece locations
     */
    public static Piece[][] getBoard() {
        return board;
    }

    /**
     * Set the piece at the correct coordinate on the boardGUI
     */
    private void setBoardPiece(Piece piece, Coordinate coordinate) {
        int x = coordinate.x;
        int y = coordinate.y;
        board[x][y] = piece;
    }

    /**
     * Marks the coordinate as empty (set it to null)
     */
    private void removeBoardPiece(Coordinate coordinate) {
        int x = coordinate.x;
        int y = coordinate.y;
        board[x][y] = null;
    }

    /**
     * Print out the boardGUI and its current state
     */
    public void printBoard() {
        String rowDivider = "   -----------------------------------------";
        System.out.println(rowDivider);
        int index = 0;
        for (Piece[] row : board) {
            System.out.print(index + " | ");
            for (Piece piece : row) {
                if (piece == null)
                    System.out.print("  ");
                else
                    System.out.print(piece.toString());
                System.out.print(" | ");
            }
            System.out.println("\n" + rowDivider);
            index++;
        }
        System.out.println("     A    B    C    D    E    F    G    H");
    }

    /**
     * ####################################
     *         Initialization Methods
     * ####################################
     */

    /**
     *
     * @param pieceType
     * @param x
     * @param y
     * @param color
     */
    public void initializePiece(String pieceType, int x, int y, String color) {
        Piece piece = null;
        switch (pieceType) {
            case Constants.PAWN:
                piece = new Pawn(x, y, color);
                break;
            case Constants.ROOK:
                piece = new Rook(color);
                break;
            case Constants.KNIGHT:
                piece = new Knight(color);
                break;
            case Constants.BISHOP:
                piece = new Bishop(color);
                break;
            case Constants.QUEEN:
                piece = new Queen(color);
                break;
            case Constants.KING:
                piece = new King(color);

                if (color.equals(Constants.WHITE))
                    whiteKing = piece;
                else
                    blackKing = piece;
                break;
            case Constants.FERZ:
                piece = new Ferz(color);
                break;
            default:
                System.err.println("ERR: Read in piece type that does not exist.");
        }

        setPiece(piece, x, y);
    }

    /**
     * Initialize a stalemate scenario for testing
     */
    private void initializeStalemateScenario() {
        initializePiece(Constants.KING, 0, 7, Constants.BLACK);
        initializePiece(Constants.KING, 2, 6, Constants.WHITE);
        initializePiece(Constants.BISHOP, 1, 5, Constants.WHITE);
    }
}

package main.pieces;

import main.model.Board;
import main.Constants;
import main.model.Coordinate;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by samir on 9/2/16.
 */
public class Pawn extends Piece {
    private int startPositionX;

    /**
     * Constructor taking in x and y values for coordinates
     */
    public Pawn(int x, int y, String color) {
        this.startPositionX = x;
        setCoordinate(new Coordinate(x, y));
        setColor(color);
        imagePath = "/" + color + "-pawn.png";
    }

    /**
     * Returns the type of this piece
     */
    public String getType() {
        return Constants.PAWN;
    }

    @Override public String toString() {
        return getColor() + "P";
    }

    /**
     * Returns an ArrayList with the coordinates of the possible moves this pawn can make
     */
    public ArrayList<Coordinate> possibleMoves() {
        ArrayList<Coordinate> moves = new ArrayList<Coordinate>();

        int x = getCoordinate().x;
        int y = getCoordinate().y;

        // White pieces only move up the boardGUI, black pieces only move down
        if (getColor().equals(Constants.WHITE))
            checkSpaces(moves, x, y, -1);
        else
            checkSpaces(moves, x, y, 1);

        Collections.sort(moves);

        return moves;
    }

    /**
     * Checks the space directly in front of the pawn to see if it's available
     * If it is and the pawn has not moved from his starting position, check 2 spaces in front of pawn
     * Also check diagonally adjacent pieces in front of pawn for enemies
     * @param moves
     * @param x
     * @param y
     * @param xDirection
     */
    private void checkSpaces(ArrayList<Coordinate> moves, int x, int y, int xDirection) {
        int x1Index = x + (1 * xDirection);

        // Check directly in front of pawn
        if (isEmptyCoordinate(x1Index, y)) {
            moves.add(new Coordinate(x1Index, y));

            // If pawn has not moved yet and space in front is also available, has option to take 2 steps
            if (startPositionX == x) {
                int x2Index = x + (2 * xDirection);
                if (isEmptyCoordinate(x2Index, y))
                    moves.add(new Coordinate(x2Index, y));
            }
        }

        addDiagonalIfEnemyOccupied(moves, x1Index, y - 1);
        addDiagonalIfEnemyOccupied(moves, x1Index, y + 1);
    }

    /**
     * Checks the pieces diagonally in front of the pawn for enemies
     * If there are enemies, it is a valid move. Else, not a valid move.
     * @param moves
     * @param x
     * @param y
     */
    public void addDiagonalIfEnemyOccupied(ArrayList<Coordinate> moves, int x, int y) {
        if (Coordinate.validCoordinate(x, y)) {
            Coordinate coordinate = new Coordinate(x, y);
            Piece piece = Board.getPiece(coordinate);

            if (piece != null && !piece.getColor().equals(getColor())) {
                moves.add(coordinate);
            }
        }
    }
}

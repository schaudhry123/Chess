package main.pieces;

import main.model.Coordinate;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by samir on 9/2/16.
 */
public class King extends Piece {
    /**
     * Constructor taking in an x and y coordinate
     */
    public King(String color) {
        setColor(color);
        imagePath = "/" + color + "-king.png";
    }

    /**
     * Returns the type of this piece
     */
    public String getType() {
        return "King";
    }

    /**
     * String representation of a King
     */
    @Override public String toString() {
        return getColor() + "K";
    }

    /**
     * Returns an ArrayList with the coordinates of the possible moves
     * that this king can make
     */
    public ArrayList<Coordinate> possibleMoves() {
        ArrayList<Coordinate> moves = new ArrayList<Coordinate>();

        int x = getCoordinate().x;
        int y = getCoordinate().y;

        // Check all spaces adjacent to king's location
        addCoordinateIfAvailable(moves, x + 1, y);
        addCoordinateIfAvailable(moves, x - 1, y);
        addCoordinateIfAvailable(moves, x, y + 1);
        addCoordinateIfAvailable(moves, x, y - 1);
        addCoordinateIfAvailable(moves, x + 1, y + 1);
        addCoordinateIfAvailable(moves, x - 1, y - 1);
        addCoordinateIfAvailable(moves, x - 1, y + 1);
        addCoordinateIfAvailable(moves, x + 1, y - 1);

        Collections.sort(moves);

        return moves;
    }
}

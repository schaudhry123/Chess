package main.pieces;

import main.model.Coordinate;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by samir on 9/2/16.
 */
public class Knight extends Piece {
    /**
     * Constructor taking in an x and y coordinate
     */
    public Knight(String color) {
        setColor(color);
        imagePath = "/" + color + "-knight.png";
    }

    /**
     * Returns the type of this piece
     */
    public String getType() {
        return "Knight";
    }

    /**
     * String representation of a Knight
     */
    @Override public String toString() {
        return getColor() + "N";
    }

    /**
     * Returns an ArrayList with the coordinates of the possible moves this knight can make
     * @return
     */
    public ArrayList<Coordinate> possibleMoves() {
        ArrayList<Coordinate> moves = new ArrayList<Coordinate>();

        int x = getCoordinate().x;
        int y = getCoordinate().y;

        // Check all 8 possible spaces the knight can move to
        addKnightCoordinates(moves, x, y);

        Collections.sort(moves);

        return moves;
    }
}

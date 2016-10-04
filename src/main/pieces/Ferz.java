package main.pieces;

import main.*;
import main.model.Coordinate;

import java.util.ArrayList;

/**
 * Created by samir on 9/13/16.
 *
 * A custom piece that moves like a bishop but can only go one square
 */
public class Ferz extends Piece {

    /**
     * Ferz constructor
     * @param color
     */
    public Ferz(String color) {
        setColor(color);
        imagePath = "/" + color + "-ferz.png";
    }

    /**
     * Return the type of piece
     * @return
     */
    public String getType() {
        return Constants.FERZ;
    }

    /**
     * String representation of a Ferz
     * @return
     */
    @Override
    public String toString() {
        return getColor() + "F";
    }

    /**
     * Return an ArrayList of Coordinates of possible moves the Ferz can make
     * @return
     */
    public ArrayList<Coordinate> possibleMoves() {
        ArrayList<Coordinate> moves = new ArrayList<Coordinate>();

        int x = getCoordinate().x;
        int y = getCoordinate().y;

        addCoordinateIfAvailable(moves, x - 1, y - 1);
        addCoordinateIfAvailable(moves, x - 1, y + 1);
        addCoordinateIfAvailable(moves, x + 1, y - 1);
        addCoordinateIfAvailable(moves, x + 1, y + 1);

        return moves;
    }
}

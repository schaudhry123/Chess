package main.pieces;

import main.*;
import main.model.Coordinate;

import java.util.ArrayList;

/**
 * Created by samir on 9/13/16.
 */
public class Princess extends Piece {

    /**
     * Princess constructor
     * @param color
     */
    public Princess(String color) {
        setColor(color);
        imagePath = "/" + color + "-princess.png";
    }

    /**
     * Return the type of piece
     * @return
     */
    public String getType() {
        return Constants.PRINCESS;
    }

    /**
     * String representation of a Ferz
     * @return
     */
    @Override
    public String toString() {
        return getColor() + "Z";
    }

    /**
     * Return an ArrayList of Coordinates of possible moves the Ferz can make
     * @return
     */
    public ArrayList<Coordinate> possibleMoves() {
        ArrayList<Coordinate> moves = new ArrayList<Coordinate>();

        int x = getCoordinate().x;
        int y = getCoordinate().y;

        addKnightCoordinates(moves, x, y);
        checkAllDiagonals(moves, x, y);

        return moves;
    }

}

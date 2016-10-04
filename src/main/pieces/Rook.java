package main.pieces;

import main.model.Coordinate;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by samir on 9/2/16.
 */
public class Rook extends Piece {
    /**
     * Constructor taking in an x and y coordinate
     */
    public Rook(String color) {
        setColor(color);
        imagePath = "/" + color + "-rook.png";
    }

    /**
     * Returns the type of this piece
     */
    public String getType() {
        return "Rook";
    }

    /**
     * String representation of a Rook
     * @return
     */
    @Override public String toString() {
        return getColor() + "R";
    }

    /**
     *
     * @return
     */
    public ArrayList<Coordinate> possibleMoves() {
        ArrayList<Coordinate> moves = new ArrayList<Coordinate>();

        int x = getCoordinate().x;
        int y = getCoordinate().y;

        checkAllRowsColumns(moves, x, y);
        Collections.sort(moves);

        return moves;
    }
}

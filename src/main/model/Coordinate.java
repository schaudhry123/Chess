package main.model;

import main.pieces.Piece;

/**
 * Created by samir on 9/1/16.
 */
public class Coordinate implements Comparable<Coordinate> {
    public int x;;
    public int y;

    /**
     * Constructor taking in an x and y coordinate
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * String representation of a Coordinate
     */
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    /**
     * Equality operator for Coordinates (equal x and y values)
     */
    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof Coordinate) {
            Coordinate coord = (Coordinate) other;
            result = (this.x == coord.x && this.y == coord.y);
        }
        return result;
    }

    /**
     * Comparator for Coordinates to allow for consistent sorting of arrays
     */
    public int compareTo(Coordinate coord) {
        int compareXVal = coord.x;
        int compareYVal = coord.y;

        if (x == compareXVal)
            return y - compareYVal;
        else
            return x - compareXVal;
    }

    /**
     * Check if the x and y values are legitimate coordinates
     */
    public static boolean validCoordinate(int x, int y) {
        Piece[][] board = Board.getBoard();
        return ( (x >= 0 && x < Board.getLength()) && (y >= 0 && y < Board.getWidth()) );
    }
}

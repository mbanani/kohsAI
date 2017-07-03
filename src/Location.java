/**
 * Intended to simplify the function I/O with regard to different locations
 * as well as to encapsulate the location so that any changes in representations
 * can be done more simply. This also allows for location methods to be written
 * here to improve the readablity of the code for other methods.
 *
 * @author Mohamed Banani
 * @date 9/17/2015
 * @version 1.0
 */

public class Location {
    int x;
    int y;

    public Location(int x, int y) {
	this.x = x;
	this.y = y;
    }

    /**
     * Returns the area that the location is based in.
     * Defined Locations:
     * 		0. Undefined
     * 		1. Hand
     * 		2. Puzzle
     * 		3. Block Bank
     * 		4. Play Area
     *
     * @param table the table
     * @return the area that's being played in
     */
    public int positionIn(Table table) {
	if(this == table.handLoc) {
	    return 1;
	} else if(this == table.puzzleLoc) {
	    return 2;
	} else if(x >= table.bankX && y >= table.bankY) {
	    return 3;
	} else if(x < table.puzzle.getSize() && y < table.puzzle.getSize()) {
	    return 4;
	} else {
	    System.out.print("Error: Searched Location (");
	    System.out.print(x);
	    System.out.print(", ");
	    System.out.print(y);
	    System.out.println(") is not in any defined area.");
	    return 0;
	}
    }

    /**
     * Checks if other Location is the same as the first
     *
     * @param other The other location being checked
     * @return true if both locations have the same (x,y) coordinates
     */
    public boolean equals(Location other){
	if(x == other.x && y == other.y){
	    return true;
	} else {
	    return false;
	}
    }

}

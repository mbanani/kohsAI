/**
 * 	The basic puzzle design class to be used.
 *	Puzzle class is to be implemented within the table class
 * 	to encapsulate each puzzle.
 *	Only square puzzles are used at this point.
 *
 *	Coordinate system in arrays is object[X][Y], with 0,0 being bottom left
 *
 *	@author  	Mohamed El Banani
 *	@date	 	24 July 2015
 *	@version 	1.0
 *	@since 		1.0
 */

public class Puzzle {

    //Puzzle parameters are the puzzle size and its design.
    private int 	size;
    private int[][] design;

    /**
     *	Puzzle constructor method.
     *	@param size the size of the puzzle
     *	@param design the puzzle design
     */
    public Puzzle(int size, int[][] design) {
	if (!checkSquare(design)) { // check if design is a square design
	    //error
	    System.out.println("Error: Puzzle Design not Square");
	} else if (size != design.length) {
	    //error
	    System.out.println("Error: Puzzle dimensions and size mismatch");
	} else if (!checkFaces(design)) {
	    //error
	    System.out.println("Error: Some of the faces in design are invalid");
	} else {
	    this.size = size;
	    this.design = design;
	}
    }

    /**
     *	Gets the face at location provided
     *	@param xLocation X-Location of face
     *	@param yLocation Y-location of face
     */
    public int getFace(int xLocation, int yLocation) {
	if (xLocation >= size || yLocation >= size) {
	    //error
	    System.out.println("Error: Input locations exceed puzzle dimensions");
	    return -1;
	} else {
	    return design[xLocation][yLocation];
	}
    }

    /**
     *	Gets the size of the puzzle
     */
    public int getSize() {
	return size;
    }


    /**
     * Helper method to determine whether or not puzzle is a square
     * @param design array representation of the design
     */
    private boolean checkSquare(int[][] design) {
	int principleLength = design.length;
	for(int i = 0; i < principleLength; i++) {
	    if (design[i].length != principleLength) {
		return false;
	    }
	}
	return true;
    }


    /**
     * Helper method to determine whether faces are valid
     * @param design array representation of the design
     */
    private boolean checkFaces(int[][] design) {
	int principleLength = design.length;
	for(int i = 0; i < principleLength; i++) {
	    for(int j = 0; j < principleLength; j++) {
		if (design[i][j] < 0 || design[i][j] > 6) {
		    return false;
		}
	    }
	}
	return true;
    }

    public int[][] fullDesign() {
	return design;
    }

}
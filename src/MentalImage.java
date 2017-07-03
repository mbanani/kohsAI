/**
 * A wrapper class for the mental image.
 * For now, image will just be a numerical array
 *
 * @author  	Mohamed El Banani
 * @date	25 July 2015
 * @version 	1.0
 * @since 	1.0
 */

public class MentalImage {

    private int[][] 	m;	// current representation of the mental image
    private int		memorySize;
    int			originX;
    int			originY;

    //bandaid
    public String	perceivedObject;


    /**
     *	creates the mental image using the perceived image
     *	***DEPENDS ON THE PERCEIVED_IMAGE CLASS
     *
     *	Assumptions:
     *	1. gaze point is bottom left corner (except for limiters
     *	2. assume mental image is square shaped
     *
     *	@param 	perception	the perceived image of the table
     *	@param 	memorySize	the size of the memory
     */
    public MentalImage(PerceivedImage perception, int memorySize) {
	this.perceivedObject = perception.perceivedObject;
	if(perception.perceivedObject == "t") {
	    originX = perception.gazeX;
	    originY = perception.gazeY;
	} else {
	    originX = perception.puzzleLoc.x;
	    originY = perception.puzzleLoc.y;
	}
	this.memorySize = memorySize;
	m = new int[memorySize][memorySize];

	// switch X location if near limit
	if ((originX + memorySize - 1)>= perception.p.length) {
	    originX = originX - memorySize + 1;
	}

	// switch Y location if near limit
	if ((originY + memorySize - 1)>= perception.p[0].length) {
	    originY = originY - memorySize + 1;
	}

	for (int i = 0; i < memorySize; i++) {
	    for (int j = 0; j < memorySize; j++) {
		int x = originX + i;
		int y = originY + j;

		// define booleans to check if coordinates are in p
		boolean xInP = (x >= 0) && (x < perception.p.length);
		boolean yInP = (y >= 0) && (y < perception.p[0].length);

		//fill mental with -1 if outside a border
		if (xInP && yInP) {
		    m[i][j] = perception.p[originX+i][originY+j];
		} else {
		    m[i][j] = -1;
		}
	    }
	}

    }

    /**
     * Gets memory size
     *
     * @return	returns the size of the memory
     */
    public int getMemorySize() {
	return memorySize;
    }


    /**
     * Checks if the desired location is stored in memory
     *
     * @param xLoc the x-location of the desired location
     * @param yLoc the y-location of the desired location
     * @return	returns true if location is stored in memory
     */
    public boolean inMemory(Location loc) {
	int xLoc = loc.x;
	int yLoc = loc.y;
	if (xLoc >= originX && xLoc < originX + memorySize) {
	    if (yLoc >= originY && yLoc < originY + memorySize) {
		return true;
	    } else {
		return false;
	    }
	} else {
	    return false;
	}
    }

    /**
     * Gets the face at the input coordinates
     *
     * @param xLoc the x-location of the desired location
     * @param yLoc the y-location of the desired location
     * @return	returns the face at that location
     */
    public int getFace(Location loc) {
	int xLoc = loc.x;
	int yLoc = loc.y;
	if (!inMemory(loc)) {
	    return -1;
	} else {
	    return m[xLoc-originX][yLoc-originY];
	}
    }
}

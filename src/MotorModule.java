/**
 * 	The basic Motor Action Module to be used.
 *	The Motor module encapsulates the agents physical interaction
 *	with the blocks and the table. (TODO) The basic motor actions to be
 *	implemented are
 *		- Flip:  	3-D rotation of the block
 *		- Rotate:	Rotation of the Face
 *		- Grab:		Hold block in one's hand
 *		- place:	Place block in a specific position
 *
 *	@author  	Mohamed El Banani
 *	@date	 	24 July 2015
 *	@version 	1.0
 *	@since 		1.0
 */

public class MotorModule {

    // Parameters for the blocks in each hand
    Block firstHand;		// for now has a location of 5,0 in holdBlock
    Block secondHand;		// for now has a location of 6,0 in holdBlock

    public MotorModule() {
	firstHand = null;
	secondHand = null;
    }


    /**
     *	Grabs the input block from the table
     *	@param xLoc the xLocation of the block to be held
     *	@param yLoc the yLocation of the block to be held
     *	@table table the table used
     */
    public boolean grabBlock(Location inLoc, Table table) {
	int xLoc = inLoc.x;
	int yLoc = inLoc.y;
	Block block = table.findBlock(xLoc, yLoc);
	if (block == null || table == null) {
	    System.out.println("Error: null input");
	    return false;
	} else if (firstHand != null && secondHand != null) {
	    System.out.println("Error: no free hands");
	    return false;
	} else if (!table.checkBlock(block)) {
	    System.out.println("Error: block not found on table");
	    return false;
	} else {
	    table.holdBlock(block);

	    // change block location
	    if (firstHand == null) {
		block.moveBlock(5,0);
		firstHand = block;
	    } else {
		block.moveBlock(6,0);
		secondHand = block;
	    }
	    block.setInHand(true);

	    return true;
	}
    }

    /**
     *	Places the input block in play area or block bank
     *	@param block the block to be grabbed
     *	@table table the table used
     */
    public boolean placeBlock(Block block, Table table, int xLoc, int yLoc) {
	if (block == null || table == null) {
	    System.out.println("Error in placeBlock(): null input");
	    return false;
	} else if (!table.validLoc(xLoc, yLoc)) {
	    System.out.println("Error in placeBlock(): Input location invalid");
	    return false;
	} else if (!table.checkFree(xLoc, yLoc)) {
	    System.out.println("Error in placeBlock(): desired position is already occupied");
	    return false;
	} else if (firstHand.equals(block)) {
	    //change block parameters
	    block.setInHand(false);
	    block.moveBlock(xLoc,yLoc);

	    // edit in table
	    table.returnBlock(block);

	    // edit from first hand
	    firstHand = null;

	    return true;
	} else if (secondHand.equals(block)) {
	    //change block parameters
	    block.setInHand(false);
	    block.moveBlock(xLoc,yLoc);

	    // edit in table
	    table.returnBlock(block);

	    // edit from first hand
	    secondHand = null;

	    return true;
	} else {
	    System.out.println("Error: block is not in hand");
	    return false;
	}
    }

    /**
     * Sets the block in the first hand to the input face
     *
     * @param face the input face
     * @return	returns true if face setting was succesful
     */
    public boolean setFirstHandFace(int face) {
	if (firstHand == null) {
	    return false;
	} else {
	    firstHand.setFace(face);
	    return true;
	}
    }

    public int rotateBlock(Block block, String direction) {
	// TODO
	block.rotateFace(direction);
	return block.getFace();
    }

    public int flipBlock(Block block, String direction) {
	// TODO
	return block.getFace();
    }


    public void placeFirstHandBlock(Location loc, Table table) {
	placeBlock(firstHand,table,loc.x,loc.y);
    }
}
/**
 * The basic block class to be used.
 * Block only has a face, a location and whether or not its being used.
 *
 * All function performed on the block are methods in this class
 *
 * @author Mohamed El Banani
 * @date 24 July 2015
 * @version 1.0
 * @since 1.0
 */

public class Block {
	/**
	 * The main features of a block.
	 * The block face can have an integer value for 1 to 6
	 * 1 : Red Face
	 * 2 : Red is pointing top right
	 * 3 : Red is pointing top left
	 * 4 : Red is pointing bottom left
	 * 5 : Red is pointing bottom right
	 * 6 : White Face
	 */
	private int face;
	private int xLoc;
	private int yLoc;
	private boolean inHand;
	private int id;

	/**
	 * Block constructor method.
	 *
	 * @param xLocation
	 *            The x location of the block
	 * @param yLocation
	 *            The y location of the block
	 * @param face
	 *            The face of the block.
	 */
	public Block(int xLocation, int yLocation, int face, int id) {
		xLoc = xLocation;
		yLoc = yLocation;
		if (face > 6 || face < 0) {
			// error message
			System.out
					.println("Error: Incorrect input for face. Face set to White");
			this.face = 6;
		} else {
			this.face = face;
		}
		this.inHand = false;
		this.id = id;
	}

	/**
	 * Moves the block from one location to another.
	 *
	 * @param newX
	 *            The new x location of the block
	 * @param newY
	 *            The new y location of the block
	 */
	public void moveBlock(int newX, int newY) {
		xLoc = newX;
		yLoc = newY;
	}

	/**
	 * Sets the block face to the input face
	 *
	 * @param newFace
	 *            ;
	 */
	public void setFace(int newFace) {
		if (newFace > 6 || newFace < 0) {
			// error message
			System.out.println("setFace failed: Incorrect Input");
		} else {
			face = newFace;
		}
	}

	/**
	 * Rotates the face clockwise or anti-clockwise.
	 *
	 * @param direction
	 *            the direction chosen("CW" or "CCW")
	 */
	public void rotateFace(String direction) {
		if (face == 6 || face == 0) {
			// error message
			System.out.println("Note: Solid Face, rotation has no impact");
		} else if (face > 6 || face < 0) {
			// error message
			System.out.println("Error: Invalid face detected.");
		} else {
			int tempFace = face - 2;
			if (direction.equals("CCW")) {
				tempFace = tempFace + 1;
				face = tempFace % 4;
			} else if (direction.equals("CW")) {
				tempFace = tempFace + 3;
				face = tempFace % 4;
			} else {
				// error message
				System.out.println("Error: Invalid input for direction");
			}
		}
	}

	/**
	 * Gets the face of the block
	 *
	 * @return the face of the block
	 */
	public int getFace() {
		return face;
	}

	/**
	 * Gets the x Location of the block
	 *
	 * @return the x-location of the block
	 */
	public int getX() {
		return xLoc;
	}

	/**
	 * Gets the y Location of the block
	 *
	 * @return the y-location of the block
	 */
	public int getY() {
		return yLoc;
	}
	
	public Location getLoc() {
		return new Location(xLoc, yLoc);
	}

	/**
	 * sets inHand to its current state
	 *
	 * @param state
	 *            current state of the block (True = block is in hand)
	 */
	public void setInHand(boolean state) {
		inHand = state;
	}

	/**
	 * Checks whether the block is in hand or not
	 *
	 * @return whether or not the block is in hand
	 */
	public boolean checkInHand() {
		return inHand;
	}

	/**
	 * Checks if two blocks are the same
	 *
	 * @param block
	 *            the block to be compared to the current one
	 * @return true if both references refer to the same block
	 */
	public boolean equals(Block block) {
		if (block == null) {
			return false;
		} else if (id == block.id) {
			return true;
		} else {
			return false;
		}
	}
}
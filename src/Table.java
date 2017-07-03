import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * The basic table class to be used. Assumptions: - Table contains discrete
 * slots, each has the size of 1 block - Coordinates: XY-plane, with 0,0 being
 * bottom left - BlockBank is in the top right corner starting from bankX,bankY
 * - Play area is in the bottom left corner, and has size of puzzle - ArrayLists
 * are used to keep track of blocks in block bank and play area
 *
 * @author Mohamed El Banani
 * @date 24 July 2015
 * @version 1.0
 * @since 1.0
 */

public class Table {
    /**
     * Table parameters
     */
    int width; // X-axis - 7 block minimum
    int length; // Y-axis - 7 block minimum
    int bankX; // Bank left bound
    int bankY; // Bank bottom bound
    int numBlocks; // Number of blocks on the table
    ArrayList<Block> blockBank; // Blocks in Block Bank
    ArrayList<Block> playArea; // Blocks in play area
    ArrayList<Block> inPlay; // Blocks currently in use
    Puzzle puzzle;
    Random rand = new Random();

    // temporary variables, till better solutions found
    // TODO remember to change corresponding locations in Location class
    Location puzzleLoc 	= new Location(7,1); //static location for now
    Location handLoc	= new Location(5,0);



    /**
     * Construct a basic table.
     *
     * @param width
     *            the width of the table (x-axis).
     * @param length
     *            the length of the table (y-axis).
     * @param numBlocks
     *            the total number of blocks on the table.
     * @param bankWidth
     *            the width of the block bank (x-axis)
     * @param bankLength
     *            the length of the block bank (x-axis)
     */
    public Table(int width, int length, int numBlocks, int bankX, int bankY) {
	if (width > 7) {
	    this.width = width;
	} else {
	    // error
	    System.out.println("Error: Invalid input for Table Width");
	}

	if (length > 7) {
	    this.length = length;
	} else {
	    // error
	    System.out.println("Error: Invalid input for Table length");
	}

	if (numBlocks > 0) {
	    this.numBlocks = numBlocks;
	} else {
	    // error
	    System.out.println("Error: Invalid input for the number of Blocks");
	}

	if (bankX > 3 && (width - bankX) > 3) {
	    this.bankX = bankX;
	} else {
	    // error
	    System.out.println("Error: Invalid input for block bank Width");
	}

	if (bankY > 3 && (length - bankY) > 3) {
	    this.bankY = bankY;
	} else {
	    // error
	    System.out.println("Error: Invalid input for block bank length");
	}

	// Create block lists
	resetTable();
    }

    /**
     * Clears the table by putting all the blocks in block bank
     */
    public void resetTable() {
	// Create block lists
	playArea = new ArrayList<Block>();
	blockBank = new ArrayList<Block>();
	inPlay = new ArrayList<Block>();

	// add blocks
	int tempWidth = width - bankX;
	int tempLength = length - bankY;
	int tempX, tempY;
	for (int i = 0; i < numBlocks; i++) {
	    tempX = bankX + rand.nextInt(tempWidth);
	    tempY = bankY + rand.nextInt(tempLength);
	    while (!checkFree(tempX, tempY)) {
		tempX = bankX + rand.nextInt(tempWidth);
		tempY = bankY + rand.nextInt(tempLength);
	    }
	    blockBank.add(new Block(tempX, tempY, 1 + rand.nextInt(6), i));
	}
    }

    /**
     * Generates an array representation of the table
     */
    public int[][] generateTable() {
	int[][] table = zeroes2D(width, length);

	// create iterators
	Iterator<Block> playItt = playArea.iterator();
	Iterator<Block> bankItt = blockBank.iterator();
	Iterator<Block> usedItt = inPlay.iterator();
	Block temp;
	// fill array
	while (playItt.hasNext()) {
	    temp = playItt.next();
	    table[temp.getX()][temp.getY()] = temp.getFace();
	}

	while (bankItt.hasNext()) {
	    temp = bankItt.next();
	    table[temp.getX()][temp.getY()] = temp.getFace();
	}

	while (usedItt.hasNext()) {
	    temp = usedItt.next();
	    table[temp.getX()][temp.getY()] = temp.getFace();
	}

	return table;
    }

    /**
     * Checks whether or not the solution reached is correct and complete
     */
    public boolean checkSolution(boolean print) {
	int[][] table = generateTable();
	int wrong = 0, missing = 0;
	if (puzzle == null) {
	    // error
	    System.out.println("Error: No puzzle to check solution against.");
	    return false;
	} else {
	    for (int x = 0; x < puzzle.getSize(); x++) {
		for (int y = 0; y < puzzle.getSize(); y++) {
		    if (table[x][y] == 0) {
			missing++;
		    } else if (table[x][y] != puzzle.getFace(x, y)) {
			wrong++;
		    }
		}
	    }
	    if (print) {
		if (missing > 0 && wrong > 0) {
		    System.out.println("Solution is wrong and incomplete.");
		    return false;
		} else if (missing > 0) {
		    System.out.println("Solution is incomplete.");
		    return false;
		} else if (wrong > 0) {
		    System.out.println("Solution is incorrect.");
		    return false;
		} else {
		    System.out.println("Solution is complete and correct.");
		    return true;
		}
	    } else {
		return (missing ==0) && (wrong == 0);
	    }
	}
    }

    /**
     * Checks to see if the block location is free
     *
     * @param xLoc
     *            X-coordinate of the location
     * @param yLoc
     *            Y-coordinate of the location
     */
    public boolean checkFree(int xLoc, int yLoc) {
	int[][] arrangement = generateTable();
	if (arrangement[xLoc][yLoc] == 0) {
	    return true;
	} else {
	    return false;
	}
    }

    /**
     * Checks if the block is on the table
     *
     * @param block
     *            The block to be checked
     */
    public boolean checkBlock(Block block) {
	if (block == null) {
	    System.out.println("Error: null input for check block");
	    return false;
	} else {
	    Iterator<Block> playItt = playArea.iterator();
	    Iterator<Block> bankItt = blockBank.iterator();
	    Block temp;

	    while (playItt.hasNext()) {
		temp = playItt.next();
		if (block.equals(temp)) {
		    return true;
		}
	    }

	    while (bankItt.hasNext()) {
		temp = bankItt.next();
		if (block.equals(temp)) {
		    return true;
		}
	    }

	    return false;
	}
    }

    /**
     * Checks if input location is in play area or in block bank
     *
     * @param xLoc
     *            X-coordinate of the location
     * @param yLoc
     *            Y-coordinate of the location
     */
    public boolean validLoc(int xLoc, int yLoc) {
	if (xLoc >= 0 && xLoc < width && yLoc >= 0 && yLoc < length) {
	    if (xLoc < puzzle.getSize() && yLoc < puzzle.getSize()) {
		return true; // block is in play area
	    } else if (xLoc >= bankX && yLoc >= bankY) {
		return true; // block is in block bank
	    } else {
		return false; // block is on table, but in neither areas
	    }
	} else {
	    return false; // block is not on the table
	}
    }

    /**
     * Moves block from play area or block bank to inPlay
     *
     * @param block
     *            the block to be moved
     */
    public boolean holdBlock(Block block) {
	if (block == null) {
	    System.out.println("Error: null input.");
	    return false;
	} else if (!checkBlock(block)) {
	    System.out.println("Error: Block cannot be found");
	    return false;
	} else {
	    Iterator<Block> playItt = playArea.iterator();
	    Iterator<Block> bankItt = blockBank.iterator();
	    Block temp;

	    while (playItt.hasNext()) {
		temp = playItt.next();
		if (block.equals(temp)) {
		    playArea.remove(block);
		    inPlay.add(block);
		    return true;
		}
	    }

	    while (bankItt.hasNext()) {
		temp = bankItt.next();
		if (block.equals(temp)) {
		    blockBank.remove(block);
		    inPlay.add(block);
		    return true;
		}
	    }

	    System.out
	    .println("Error: Check arraylist traversal in holdBlock()");
	    return false;
	}
    }

    /**
     * returns block to play area or to the block bank
     *
     * @param block
     *            the block to be moved
     */
    public boolean returnBlock(Block block) {
	if (block == null) {
	    System.out.println("Error: null input.");
	    return false;
	} else if (!validLoc(block.getX(), block.getY())) {
	    System.out.println("Error: Block cannot be returned there");
	    return false;
	} else {
	    Iterator<Block> usedItt = inPlay.iterator();
	    Block temp;

	    while (usedItt.hasNext()) {
		temp = usedItt.next();
		if (block.equals(temp)) {
		    inPlay.remove(block);

		    if (block.getX() < puzzle.getSize()
			    && block.getY() < puzzle.getSize()) {
			playArea.add(block);
			return true;
		    } else if (block.getX() >= bankX && block.getY() >= bankY) {
			blockBank.add(block);
			return true;
		    } else {
			System.out
			.println("Error: Something off in returnBlock()");
			return false;
		    }
		}
	    }

	    System.out.println("Error: Something off in returnBlock() #2");
	    return false;
	}
    }

    /**
     * Finds the block at location xLoc,yLoc
     *
     * @param xLoc
     *            the xLocation of the block
     * @param yLoc
     *            the yLocation of the block
     * @return the block at the input location
     */
    public Block findBlock(int xLoc, int yLoc) {
	Iterator<Block> bankItt = blockBank.iterator();
	Block currentBlock = null;

	while (bankItt.hasNext()) {
	    currentBlock = bankItt.next();

	    if (currentBlock.getX() == xLoc && currentBlock.getY() == yLoc) {
		return currentBlock;
	    }
	}

	return currentBlock;
    }

    /**
     * Prints the state of the table at the input time
     *
     * @param (int) time the time at which the state of the board is printed
     */
    public void printState(double time) {
	int[][] table = generateTable();
	System.out.print("Table State at Time: ");
	System.out.println((int) time);
	System.out.println();
	for (int j = length - 1; j >= 0; j--) {
	    for (int i = 0; i < width; i++) {
		if (table[i][j] == 0) {
		    System.out.print('.');
		} else {
		    System.out.print(table[i][j]);
		}
		System.out.print(" ");
	    }
	    System.out.println();
	}
	System.out.println("---------------------------------");
	System.out.println();
    }

    /**
     * helper method that initaties a 2-D array to all 0s
     */
    public int[][] zeroes2D(int width, int length) {
	int[][] array = new int[width][length];
	for (int i = 0; i < width; i++) {
	    for (int j = 0; j < length; j++) {
		array[i][j] = 0;
	    }
	}
	return array;
    }

    public boolean setPuzzle(Puzzle puzzle) {
	if (puzzle == null) {
	    return false;
	} else {
	    this.puzzle = puzzle;
	    return true;
	}
    }
}
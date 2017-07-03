/**
 * The basic AI class(The Central Executive Module in the proposal)
 *
 * This class is responsible for planning and decision making. Module action as
 * described by Dr Maithilee Kunda: 
 * At each time step, module will examine the possible actions which are 
 * 		- Changing the level of abstraction in the perception module 
 * 		- Changing the gaze location in the attention module 
 * 		- Store/manipulate/flush the mental image 
 * 		- Perform a physical action on the blocks through the Motor Module
 *
 * The majority of the methods capture higher level actions to make it easier to
 * write different strategies/solution methods, as well as to easily incorporate
 * performance metrics
 *
 * Primitive actions to be used in strategies.
 * 	motor actions:
 * 		grabBlock(loc)			{return nothing}
 * 		placeBlock(loc)			{return nothing}
 * 		rotateBlock(loc)		{return nothing}
 * 	gaze actions:
 * 		findEmptySpace() 		{return loc}
 * 		findBlock() or (face)	{return loc}
 * 		lookAt(loc)				{return face}
 * 		lookAtPuzzle()			{return face}
 * 		lookAtHand()			{return face}
 * 	mental actions:
 * 		NONE CURRENTLY (TODO)
 *
 * TODO:
 * 1. Module is to have a symbolic AI architecture, with method for action
 * generation, ranking and selection.
 * 2. Mental Operations Higher-order methods
 *
 * @author Mohamed El Banani
 * @date 5 August 2015
 * @version 1.0
 * @since 1.0
 */

public class AI {

	/**
	 * Parameters are the other modules and the external environment (Table)
	 */
	AttentionModule attention;
	MotorModule action;
	MentalModule mental;
	PerceptionModule perception;
	Table table;
	Stats stats; // time variable
	WorkingMemory wM; // replacement for single faces for now

	/**
	 * Constructor for the AI class
	 *
	 * @param memory
	 *            The size of the memory of the AI class
	 */
	public AI(int memory, Table table) {
		attention = new AttentionModule();
		action = new MotorModule();
		mental = new MentalModule(memory);
		perception = new PerceptionModule();
		this.table = table;
		stats = new Stats(this);
		wM = new WorkingMemory();
	}

	/**
	 * Generic function that will the strategy of the AI.
	 * TODO: Can be later used for meta-cognition.
	 * @return A solution frame outlining the AI's solution
	 */
	public SolutionFrame solve() {
		return null;
	}


	/**
	 * Primitive Actions
	 */

	/**
	 * Grabs a block from the puzzle
	 *
	 * @param newLoc The location of the block
	 */
	public void grabBlock(Location newLoc) {
		//switch attention to block and grab it
		if(attention.getLoc().equals(newLoc)){
			action.grabBlock(newLoc, table);
		}else {
			stats.addGazeShift(newLoc, attention.getLoc());
			attention.switchGaze(newLoc);
			action.grabBlock(newLoc, table);
		}

		//look at grabbed block
		attention.switchGaze(table.handLoc);
		stats.addGazeShift(newLoc, attention.getLoc());

		//add a move
		stats.addMove(newLoc, table.handLoc);
	}

	/**
	 * Places a block on the table
	 */
	public void placeBlock(Location newLoc) {
		//look at hand (TODO verify assumption)
		if(!attention.getLoc().equals(table.handLoc)){
			stats.addGazeShift(attention.getLoc(), table.handLoc);
			attention.switchGaze(table.handLoc);
		}

		stats.addMove(table.handLoc, newLoc);
		stats.addGazeShift(table.handLoc, newLoc);
		action.placeFirstHandBlock(newLoc, table);
		attention.switchGaze(newLoc);
	}

	/**
	 * Rotate the block to the desired face.
	 * TODO highly simplified, consider more sophisticated ways
	 *
	 * @param newFace the face to change the block to
	 */
	public void rotateBlock(int newFace){
		changeFace(newFace);
	}

	public void changeFace(int newFace) {
		// keep rotations at 1 regardless of face change
		stats.addRotation(1);
		action.setFirstHandFace(newFace);
	}

	/**
	 * Looks at location, loc, in the puzzle
	 *
	 * @param loc		the location that will be looked at
	 * @param memoryStore	the memory storage that will be used
	 * @return			the face of the puzzle
	 */
	public int lookAtPuzzle(Location loc, String memoryStore) {
		if(memoryStore == "STM"){
			if (mental.mentalImage != null && mental.mentalImage.perceivedObject == "p" && mental.mentalImage.inMemory(loc)) {
				return mental.mentalImage.getFace(loc);
			} else {
				//switch attention to puzzle
				stats.addGazeShift(attention.getLoc(), table.puzzleLoc);
				attention.switchGaze(table.puzzleLoc);

				//perceive
				PerceivedImage perceived = perception.perceivePuzzle(loc, table);
				mental.storeImage(perceived);

				return mental.mentalImage.getFace(loc);
			}
		} else if(memoryStore == "WM") {
			// Move Gaze to puzzle
			stats.addGazeShift(attention.getLoc(), table.puzzleLoc);
			attention.switchGaze(table.puzzleLoc);

			// perceive face (For simplified for now)
			wM.face = table.puzzle.fullDesign()[loc.x][loc.y];
			return wM.face;
		} else {
			System.out.println("Error in findPuzzleFace(): Memory Store input is wrong");
			return -1;
		}
	}

	/**
	 * Looks at a specific location on the table
	 *
	 * @param loc 		the location on the table
	 * @param memoryStore	the memory store that will be used ("STM" or "WM")
	 * @return	the face found
	 */
	public int lookAtTable(Location loc, String memoryStore) {
		if(memoryStore == "STM"){
			if (mental.mentalImage != null && mental.mentalImage.perceivedObject == "t" && mental.mentalImage.inMemory(loc)) {
				return mental.mentalImage.getFace(loc);
			} else {
				//switch attention to puzzle
				stats.addGazeShift(attention.getLoc(), table.puzzleLoc);
				attention.switchGaze(table.puzzleLoc);

				//perceive
				PerceivedImage perceived = perception.perceive(table, attention);
				mental.storeImage(perceived);

				return mental.mentalImage.getFace(loc);
			}
		} else if(memoryStore == "WM") {
			// Move Gaze to puzzle
			if(!attention.getLoc().equals(loc)) {
				stats.addGazeShift(attention.getLoc(), loc);
				attention.switchGaze(loc);
			}

			// perceive face (For simplified for now)
			wM.face = table.generateTable()[loc.x][loc.y];
			return wM.face;
		} else {
			System.out.println("Error in lookAtTable(): Memory Store input is wrong");
			return -1;
		}
	}

	/**
	 * Look at hand (only uses WM for that)
	 *
	 * @param handID which hand to look at (either 1 or 2)
	 * @return face in hand
	 */
	public int lookAtHand(int handID) {
		if(handID == 1){
			stats.addGazeShift(attention.getLoc(), table.handLoc);
			attention.switchGaze(table.handLoc);
			if (action.firstHand == null) {
				System.out.println("Warning in lookAtHand(): first hand is empty");
				wM.face = 0;
				return wM.face;
			} else {
				wM.face = action.firstHand.getFace();
				return wM.face;
			}
		} else if(handID == 2){
			//TODO later when it's used, no need to worry now
			System.out.println("Error in lookAtHand(): Second hand is not currently used");
			wM.face = 0;
			return -1;
		} else {
			System.out.println("Error in lookAtHand(): false handID");
			wM.face = 0;
			return -1;
		}
	}


	/**
	 * Finds a block using visual search
	 * Assumptions: Visual Search dependence on memory is nigligable (Woodman, Vogel, Luck, 2001)
	 *
	 * @param face the face being search, random if 0 is used
	 * @return the location of a block (with desired face, if found)
	 */
	public Location findBlock(int face) {
		//find a random block if face == 0
		Location oldPOR = attention.getLoc();
		if(face == 0) {
			attention.findRandomBlock(table, 0);
			stats.addGazeShift(oldPOR, attention.getLoc());
			return attention.getLoc();
		} else {
			int index = 0;
			while(attention.findRandomBlock(table, index)) {
				//update stats
				stats.addGazeShift(oldPOR, attention.getLoc());
				oldPOR = attention.getLoc();
				//check face
				wM.face = lookAtTable(attention.getLoc(), "WM");
				if(wM.face == face) {
					return attention.getLoc();
				} else {
					index++;
				}
			}

			//if not found
			return attention.getLoc();
		}
	}

	/**
	 * TODO: Mental Operations
	 */

	/**
	 * TODO: Change abstraction ?!
	 */


	/*
	 * Helper Functions
	 */

	/**
	 * updates the solution frame.
	 *
	 * @param currentSol
	 *            the current solution frame
	 * @return the next solution frame
	 */
	protected SolutionFrame updateSolutionFrame(SolutionFrame currentSol) {
		currentSol.next = new SolutionFrame(table, stats);
		return currentSol.next;
	}

}
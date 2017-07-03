/**
 * The basic AI class(The Central Executive Module in the proposal)
 *
 * This class is responsible for planning and decision making. Module action as
 * described by Dr Maithilee Kunda: At each time step, module will examine the
 * possible actions which are - Changing the level of abstraction in the
 * perception module - Changing the gaze location in the attention module -
 * Store/manipulate/flush the mental image - Perform a physical action on the
 * blocks through the Motor Module
 *
 * The majority of the methods capture higher level actions to make it easier to
 * write different strategies/solution methods, as well as to easily incorporate
 * performance metrics
 *
 * TODO:
 * 1. Module is to have a symbolic AI architecture, with method for action
 * generation, ranking and selection.
 * 2. Mental Operations Higher-order methods
 *
 *
 * @author Mohamed El Banani
 * @date 5 August 2015
 * @version 1.0
 * @since 1.0
 */

public class basicAI extends AI {

    /**
     * Parameters are the other modules and the external environment (Table)
     */
    //    AttentionModule attention;
    //    MotorModule action;
    //    MentalModule mental;
    //    PerceptionModule perception;
    //    Table table;
    //    Stats stats; // time variable
    //    WorkingMemory wM; // replacement for single faces for now

    /**
     * Constructor for the AI class
     *
     * @param memory
     *            The size of the memory of the AI class
     */
    public basicAI(int memory, Table table) {
	super(memory, table);
    }

    /*
     * Solution Strategies / Solve methods
     */

    @Override
    public SolutionFrame solve() {
	return basicStrategy();
    }

    /**
     * The most basic solution strategy. Implemented to test the system and
     * check for any problems, as well as to make sure all the modules are used.
     * The strategy is to pick a (random) block, rotate it to the desired face
     * in the puzzle, and place it accordingly, while creating an appropriate
     * output
     *
     * TODO: Uses working memory till I understand how memory will be
     * Incorporated better
     *
     * @return The solution frames describing the solution
     */
    public SolutionFrame basicStrategy() {
	SolutionFrame sol = new SolutionFrame(table, stats);
	SolutionFrame currentSol = sol;
	wM.loc = new Location(0,0);

	// postionIn() == 4 for play area
	while (!table.checkSolution(false) && wM.loc.positionIn(table) == 4) {
	    if (lookAtTable(wM.loc, "WM") == 0) {
			//findBlock(lookAtPuzzle(wM.loc, "STM"));
			findBlock(0);
		// get the block in question
		grabBlock(attention.getLoc());
		//Use STM for puzzle faces
		wM.face = lookAtHand(1);
		if(wM.face != lookAtPuzzle(wM.loc, "STM")){
		    rotateBlock(lookAtPuzzle(wM.loc, "STM"));
		}
		// show progress
		currentSol = updateSolutionFrame(currentSol);
		placeBlock(wM.loc);
	    }


	    // increment location
	    wM.loc.x++;
	    if (wM.loc.positionIn(table) != 4) {
	    	wM.loc.x = 0;
	    	wM.loc.y++;
	    }
	    currentSol = updateSolutionFrame(currentSol);
	}

	table.checkSolution(true);
	return sol;
    }

    /**
     * Main Method
     */
    public static void main(String[] args) {
	//Set up problem
	Table table = new Table(9, 9, 9, 5, 5);
	//int[][] puzzleArray = {{4,5,2}, {3, 2, 4}, {5, 4, 2}};
	int[][] puzzleArray = {{5,3,4}, {4, 2, 5}, {2, 4, 3}};
	Puzzle puzzle = new Puzzle(3, puzzleArray);
	table.setPuzzle(puzzle);

	//set up solver and solve problem
	basicAI solver = new basicAI(3, table);
	SolutionFrame sol = solver.solve();

	//Animate Solution and print output
	Animator animation = new Animator(500, sol, 50);
	animation.animate();
	sol.stats.printGazeMatrix();

    }

}
/**
 * The Stats class captures the different evaluation metrics that will be used
 * to compare the different solution strategies. Those include time, number of
 * saccades, as well as others that will be added with time. The primary reason
 * to encapsulate all those metrics in a different class is to insure a uniform
 * evaluation of all metrics, and a clear definition of each methods.
 *
 * @author Mohamed El Banani
 * @date 8 August 2015
 * @version 1.0
 * @since 1.0
 */

public class Stats {

	double time;
	AI ai;	//the AI for which those stats are being developed

	/**
	 * Attempt 1 to quantify different strategies.
	 *
	 */
	int blockMovements;// move Block from one area to another
	int majorGazeShifts; // gaze shift from one area to another
	int minorGazeShifts; // gaze shift within a single area
	int blockRotations; // rotate block
	int memoryUploads; // put blocks in memory
	// time increments from literature/studies/estimates (?) {kept at 1 for now}
	// times are written as a function of something else.
	// TODO check with maithilee
	double blockMovementTime = 1; // time per block length
	double majorGazeShiftTime = 1; // time per block length
	double minorGazeShiftTime = 1; // time per block length
	double blockRotationTime = 1; // time per 90 degree rotation ?!
	double memoryUploadTime = 1; // time per single face upload
	double gazeThreshold = 2; // the threshold between minor/major sifts

	//New Metrics
	// gazeMatrix: 	A matrix showing the gaze transitions between the different
	// 				areas on the table.
	//				(0: Undefined, 1:Hand, 2:Puzzle, 3:BlockBank, 4:Play Area)
	int[][] gazeMatrix;

	public Stats(AI ai) {
		time = 0;
		blockMovements = 0;
		majorGazeShifts = 0;
		minorGazeShifts = 0;
		blockRotations = 0;
		memoryUploads = 0;
		gazeMatrix = new int[5][5];
		this.ai = ai;
	}

	/**
	 * Increments the time by an input increment
	 *
	 * @param d
	 *            increment of time to be added
	 */
	public void addTime(double d) {
		time = time + d;
	}

	/**
	 * Add a gaze to the stats
	 *
	 * @param loc1
	 *            the location of the first point of regard
	 * @param loc2
	 *            the location of the second point of regard
	 */
	public void addGazeShift(Location loc1, Location loc2) {
		double distance = distance(loc1, loc2);

		if (distance <= 0) {
			Location hand = new Location(5,0);
			if(!loc1.equals(hand) && !loc2.equals(hand)) {
				System.out.print("Error: addGaze not possible between (");
				System.out.print(loc1.x);
				System.out.print(',');
				System.out.print(loc1.y);
				System.out.print(") and (");
				System.out.print(loc2.x);
				System.out.print(',');
				System.out.print(loc2.y);
				System.out.println(").");
			}

		} else if (distance < gazeThreshold) {
			minorGazeShifts++;
			addTime(distance * minorGazeShiftTime);
		} else {
			majorGazeShifts++;
			addTime(distance * majorGazeShiftTime);
		}

		//update transition matrix
		gazeMatrix[loc1.positionIn(ai.table)][loc2.positionIn(ai.table)]++;
	}

	public void addGazeShift(int x1, int y1, int x2, int y2) {
		addGazeShift(new Location(x1, y1), new Location (x2, y2));
	}

	/**
	 * Add a block movement
	 *
	 * @param loc1
	 *            the location of the first point of regard
	 * @param loc2
	 *            the location of the second point of regard
	 */
	public void addMove(Location loc1, Location loc2) {
		double distance = distance(loc1, loc2);

		if (distance <= 0) {
			System.out.print("Error: addMove() not possible between (");
			System.out.print(loc1.x);
			System.out.print(',');
			System.out.print(loc1.y);
			System.out.print(") and (");
			System.out.print(loc2.x);
			System.out.print(',');
			System.out.print(loc2.y);
			System.out.println(").");
		} else {
			blockMovements++;
			addTime(distance * blockMovementTime);
		}
	}

	public void addMove(int x1, int y1, int x2, int y2) {
		addMove(new Location(x1, y1), new Location (x2, y2));
	}

	/**
	 * Adds a rotation to the stats
	 *
	 * @param rotations The number of single rotations
	 */
	public void addRotation(int rotations) {
		if (rotations <= 0) {
			System.out
			.println("Error: addRotations not possible (rotations =< 0).");
		} else {
			blockRotations++;
			addTime(rotations * blockRotationTime);
		}
	}

	/**
	 * Calculates the distance between two points
	 *
	 * @param X1
	 *            first X-coordinate
	 * @param Y1
	 *            first Y-coordinate
	 * @param X2
	 *            second X-coordinate
	 * @param Y2
	 *            second Y-coordinate
	 */
	private double distance(Location loc1, Location loc2) {
		int deltaX = loc1.x - loc2.x;
		int deltaY = loc1.y - loc2.y;
		double deltaDist2 = Math.pow(deltaX, 2) + Math.pow(deltaY, 2);
		return Math.pow(deltaDist2, 0.5);
	}

	/**
	 * Prints the Gaze Matrix
	 */
	public void printGazeMatrix() {

		System.out.println();
		System.out.println("\tNA\thand\tpuzzle\tbank\tPlayArea");

		for(int i = 0; i < 5; i++) {
			switch (i){
			case(0): System.out.print("NA\t");break;
			case(1): System.out.print("hand\t");break;
			case(2): System.out.print("puzzle\t");break;
			case(3): System.out.print("bank\t");break;
			case(4): System.out.print("play\t");break;
			}
			for(int j = 0; j< 5; j++) {
				if(gazeMatrix[i][j] == 0) {
					System.out.print('.');
				} else {
					System.out.print(gazeMatrix[i][j]);
				}
				System.out.print("\t");
			}
			System.out.println();
		}
	}


}
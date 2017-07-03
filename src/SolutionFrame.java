/**
 * A linked list representing the solution frames presented in the problem
 *
 * @author Mohamed El Banani
 * @date 6 August 2015
 * @version 1.0
 * @since 1.0
 */

public class SolutionFrame {

	int width;
	int length;
	int[][] currentTable;
	SolutionFrame next;
	int time;
	Stats stats;

	public SolutionFrame(Table table, Stats stats) {
		width = table.width;
		length = table.length;
		currentTable = table.generateTable();
		next = null;
		this.time = (int) stats.time;
		this.stats = stats;
	}

}

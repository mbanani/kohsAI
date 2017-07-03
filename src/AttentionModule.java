/**
 * The basic Attention Module to be used.
 * The attentional model controls the point of regard (gaze), as well as
 * executing different gaze shifts (both minor and major saccades). The
 * first model will only allow for a basic gaze switch.
 *
 * @author  	Mohamed El Banani
 * @date 	25 July 2015
 * @version 	1.0
 * @since	1.0
 */

public class AttentionModule {
    private Location gazeLoc;

    /**
     *	Constructor Class
     */
    public AttentionModule() {
	gazeLoc = new Location(0,0);
    }

    /**
     *	Move gaze
     *	Agent is allowed to look outside the table
     *
     *	@param newX the new X-coordinate for the point-of-regard
     *	@param newY the new Y-coordinate for the point-of-regard
     */
    public void switchGaze(int newX, int newY) {
	gazeLoc = new Location(newX, newY);
    }

    public void switchGaze(Location newPOR) {
	gazeLoc = newPOR;
    }

    /**
     *	Switches gaze to a random block
     *	(TODO) simplified to a direct look-up in table arrays
     *
     *	@return whether or not a random block was found
     */
    public boolean findRandomBlock(Table table, int index) {
	if(table.blockBank.size() > index) {
	    Block block = table.blockBank.get(index);
	    switchGaze(block.getLoc());
	    return true;
	} else {
	    return false;
	}
    }

    /**
     * Get the x-location of the Gaze
     *
     * @return the x-location of the gaze
     */
    public int getX() {
	return gazeLoc.x;
    }

    /**
     * Get the y-location of the Gaze
     *
     * @return the y-location of the gaze
     */
    public int getY() {
	return gazeLoc.y;
    }

    /**
     * Returns the current gazeLocation;
     *
     * @return the location of gaze
     */
    public Location getLoc() {
	return gazeLoc;
    }

}
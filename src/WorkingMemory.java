/**
 * Accounts for the working memory used while performing a task. In its current
 * form, the working memory encodes a single location, as well as a single face.
 *
 * TODO: figure out how to deal with it once it's expanded further, or reduced
 * to a single instantiation at a time.
 *
 * @author Mohamed El Banani
 * @date 28 August 2015
 * @version 1.0
 * @since 1.0
 */
public class WorkingMemory {

    public int face;
    public Location loc;

    public WorkingMemory() {
	face = -1;
	loc = new Location(-1,-1);
    }

}

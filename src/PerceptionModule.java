
/**
 * The Basic Class for the Visual Perceptual Module of the Intelligent Agent.
 * Perceptual Model is responsible for the creation of perceived images with
 * respect to the current point of regard (from the attentional model) at
 * different levels of abstractions. The first model will ignore the effects
 * of both abstraction and attention.
 *
 * @author  	Mohamed El Banani
 * @date	25 July 2015
 * @version 	1.0
 * @since 	1.0
 */

public class PerceptionModule {
    // Perception Parameters
    int abstraction;		//to be later defined
    int MAX_ABSTRACTION = 10;	// Place-holder
    int MIN_ABSTRACTION = 0;	// Place-holder

    /* 	Physics Features to include:
     *	- Should be included if found to be relevant
     *	- Include Foveal angles => Acting as a blurry-Periphery filter
     *		* Might be used to construct a certainty perecption table
     *	- Consider reincorporation of uncertainty matrix
     *		* This would be used the same way action accuracy is used in ML
     *		* for a certainty of N:
     *			- Correct perception with probability of N
     *			- incorrect perception with probability of (1-N)/(#faces - 1)
     */

    /**
     *	Constructor for the Perception Module of the AI
     */
    public PerceptionModule() {
	abstraction = 0;
    }

    /**
     *	Looks at the puzzle to create an perceived Image
     * 	ignored attentional effects for now, and output the table
     *	@param table The table being used for the task
     *	@param attention The attentional model of the agent
     */
    public PerceivedImage perceive(Table table, AttentionModule attention) {
	return new PerceivedImage(table.generateTable(), attention);
    }

    /**
     *	Increase the level of abstraction
     */
    public boolean moreAbstract() {
	if (abstraction >= MAX_ABSTRACTION) {
	    abstraction = MAX_ABSTRACTION;
	    return false;
	} else {
	    abstraction = abstraction + 1;
	    return true;
	}
    }

    /**
     *	decrease the level of abstraction
     */
    public boolean lessAbstract() {
	if (abstraction <= MIN_ABSTRACTION) {
	    abstraction = MIN_ABSTRACTION;
	    return false;
	} else {
	    abstraction = abstraction - 1;
	    return true;
	}
    }

    /**
     * Perceives the face at location x,y for the puzzle
     *
     * @param loc	the location of the puzzle face
     * @param table	the table being used
     * @return	the face of the puzzle at location x,y
     */
    public PerceivedImage perceivePuzzle(Location loc, Table table) {
	PerceivedImage perception = new PerceivedImage(table.puzzle.fullDesign(), null);
	perception.perceivedObject = "p";
	perception.puzzleLoc = loc;
	return perception;
    }

}